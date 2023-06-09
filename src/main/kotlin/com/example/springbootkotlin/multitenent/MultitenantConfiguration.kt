package com.example.springbootkotlin.multitenent

import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.jdbc.DataSourceBuilder
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.nio.file.Paths
import java.util.*
import javax.sql.DataSource


@Configuration
class MultitenantConfiguration {
    @Value("\${defaultTenant}")
    private val defaultTenant: String? = null
    @Bean
    @ConfigurationProperties(prefix = "tenants")
    fun dataSource(): DataSource {
        val files: Array<out File>? = Paths.get("allTenants").toFile().listFiles()
        val resolvedDataSources: MutableMap<Any?, Any> = HashMap()
        if (files != null) {
            for (propertyFile in files) {
                val tenantProperties = Properties()
                val dataSourceBuilder = DataSourceBuilder.create()
                try {
                    tenantProperties.load(FileInputStream(propertyFile))
                    val tenantId = tenantProperties.getProperty("name")
                    println("tenantId $tenantId")
                    println("datasource.url ${tenantProperties.getProperty("datasource.url")}")
                    dataSourceBuilder.driverClassName(tenantProperties.getProperty("datasource.driver-class-name"))
                    dataSourceBuilder.username(tenantProperties.getProperty("datasource.username"))
                    dataSourceBuilder.password(tenantProperties.getProperty("datasource.password"))
                    dataSourceBuilder.url(tenantProperties.getProperty("datasource.url"))
                    resolvedDataSources[tenantId] = dataSourceBuilder.build()
                } catch (exp: IOException) {
                    throw RuntimeException("Problem in tenant datasource:$exp")
                }
            }
        }
        val dataSource: AbstractRoutingDataSource = MultitenantDataSource()
        dataSource.setDefaultTargetDataSource(resolvedDataSources[defaultTenant]!!)
        dataSource.setTargetDataSources(resolvedDataSources)
        dataSource.afterPropertiesSet()
        return dataSource
    }
}
