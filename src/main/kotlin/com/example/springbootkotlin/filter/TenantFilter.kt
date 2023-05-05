package com.example.springbootkotlin.filter

import com.example.springbootkotlin.multitenent.TenantContext
import org.springframework.core.annotation.Order
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest


@Component
@Order(1)
internal class TenantFilter : Filter {
    val tenantContext = TenantContext

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(
        request: ServletRequest, response: ServletResponse?,
        chain: FilterChain
    ) {
        val req = request as HttpServletRequest
        val tenantName = req.getHeader("X-TenantID")
        tenantContext.setCurrentTenant(tenantName)
        try {
            chain.doFilter(request, response)
        } finally {
            tenantContext.setCurrentTenant("")
        }
    }
}
