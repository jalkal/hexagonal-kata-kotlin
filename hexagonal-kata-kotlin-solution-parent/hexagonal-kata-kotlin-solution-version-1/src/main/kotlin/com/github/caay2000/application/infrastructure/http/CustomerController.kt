package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.CustomerApplication
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class CustomerController(private val customerApplication: CustomerApplication) {

    fun getCustomerByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getCustomerByAccountId(accountId)

        call.respond(result)
    }

    fun getProductsByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getProductsByAccountId(accountId)

        call.respond(result)
    }

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getInvoiceByAccountId(accountId)

        call.respond(result)
    }
}
