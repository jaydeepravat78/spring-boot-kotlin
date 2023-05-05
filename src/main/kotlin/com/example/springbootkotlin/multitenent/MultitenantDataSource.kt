package com.example.springbootkotlin.multitenent

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource


class MultitenantDataSource : AbstractRoutingDataSource() {

    private val tenantContext = TenantContext
    override fun determineCurrentLookupKey(): String? {
        return tenantContext.getCurrentTenant()
    }
}

