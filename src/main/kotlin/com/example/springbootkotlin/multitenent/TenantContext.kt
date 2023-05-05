package com.example.springbootkotlin.multitenent

import kotlin.concurrent.getOrSet

object TenantContext {
    private val CURRENT_TENANT = ThreadLocal<String>()

    fun getCurrentTenant(): String? {
        return CURRENT_TENANT.get() }

    fun setCurrentTenant(tenant: String) {
        CURRENT_TENANT.set(tenant)
    }
}
