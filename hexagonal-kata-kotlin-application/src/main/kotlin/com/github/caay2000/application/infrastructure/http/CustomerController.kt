package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.CustomerApplication
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.ProductData
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class CustomerController(private val customerApplication: CustomerApplication) {

    fun getCustomerByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getCustomerByAccountId(accountId)

        call.respond(result.toJson())
    }

    fun getProductsByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

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

    private fun List<ProductData>.toJson(): String {
        return """
            ${this.map { """{ "id": ${it.id}, "name": "${it.name}", "price": ${it.price} }""" }}
        """.trimIndent()

        // [ProductData(id = 100, name = TV, price = 80), ProductData(id = 200, name = LAND LINE, price = 20), ProductData(id = 300, name = PHONE, price = 60)]
    }
}
