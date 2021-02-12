package com.github.caay2000.application.infrastructure

import com.github.caay2000.application.domain.CustomerApplication
import com.github.caay2000.external.model.Account

class CustomerController {

    private val application = CustomerApplication()

    fun getCustomerByAccountId(accountId: String): String {
        val result = application.getProductsByAccountId(accountId)
        return result.toJson()
    }

    private fun Account.toJson(): String {

        return """
        {
            "accountId": "${this.accountId}",
            "customerName": "${this.customerName}"
        }
        """.trimIndent()
    }
}
