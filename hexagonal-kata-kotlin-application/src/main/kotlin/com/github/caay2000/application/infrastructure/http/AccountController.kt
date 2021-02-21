package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.api.inbound.port.AccountApi
import com.github.caay2000.application.api.model.toAccountId
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class AccountController(private val accountApi: AccountApi) {

    fun getAccountByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApi.getAccount(accountId.toAccountId())

        call.respond(result)
    }

    fun getProductsByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApi.getProducts(accountId.toAccountId())

        call.respond(result)
    }

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApi.getInvoice(accountId.toAccountId())

        call.respond(result)
    }
}
