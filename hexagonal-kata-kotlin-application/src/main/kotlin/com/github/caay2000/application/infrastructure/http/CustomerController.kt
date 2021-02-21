package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.AccountApplication
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext

class AccountController(private val accountApplication: AccountApplication) {

    fun getAccountByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApplication.getAccountByAccountId(accountId)

        call.respond(result)
    }

    fun getProductsByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApplication.getProductsByAccountId(accountId)

        call.respond(result)
    }

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApplication.getInvoiceByAccountId(accountId)

        call.respond(result)
    }
}
