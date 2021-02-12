package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.CustomerApplication
import com.github.caay2000.external.model.Account
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class CustomerController(private val customerApplication: CustomerApplication) {

    fun getCustomerByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getProductsByAccountId(accountId)

        call.respond(result.toJson())
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
