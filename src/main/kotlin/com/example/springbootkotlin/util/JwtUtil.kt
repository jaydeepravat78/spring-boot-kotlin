package com.example.springbootkotlin.util

import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.Date

@Component
class JwtUtil {
    private val SECRET_KEY = "secret"

    fun extractUsername(token: String?): String {
        return extractClaim(token, java.util.function.Function<Claims, Any> { obj: Claims -> obj.subject }).toString()
    }

    fun extractExpiration(token: String?): Date {
        return extractClaim(token, java.util.function.Function<Claims, Any> { obj: Claims -> obj.expiration }) as Date
    }

    fun <T : Any> extractClaim(token: String?, claimsResolver: java.util.function.Function<Claims, T>): T {
        val claims = extractAllClaims(token)
        return claimsResolver.apply(claims)
    }

    private fun extractAllClaims(token: String?): Claims {
        return Jwts.parser().setSigningKey(SECRET_KEY).parseClaimsJws(token).body
    }

    private fun isTokenExpired(token: String?): Boolean? {
        return extractExpiration(token).before(Date())
    }

    fun generateToken(userDetails: UserDetails): String? {
        val claims: MutableMap<String, Any> = HashMap()
        claims["Roles"] = userDetails.authorities
        return createToken(claims, userDetails.username)
    }

    private fun createToken(claims: Map<String, Any>, subject: String): String? {
        return Jwts.builder().setClaims(claims).setSubject(subject).setIssuedAt(Date(System.currentTimeMillis()))
            .setExpiration(Date(System.currentTimeMillis() + 1000 * 60 * 60 * 10))
            .signWith(SignatureAlgorithm.HS384, SECRET_KEY).compact()
    }

    fun validateToken(token: String?, userDetails: UserDetails): Boolean? {
        val username = extractUsername(token)
        return username == userDetails.username && !isTokenExpired(token)!!
    }
}
