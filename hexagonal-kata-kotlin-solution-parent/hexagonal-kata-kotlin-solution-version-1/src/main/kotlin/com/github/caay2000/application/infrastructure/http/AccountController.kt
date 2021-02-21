package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.api.external.port.AccountApi
import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.Product
import com.github.caay2000.application.api.model.toAccountId
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.math.BigDecimal

class AccountController(private val accountApi: AccountApi) {

    fun getAccountByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApi.getAccount(accountId.toAccountId())

        call.respond(result.toAccountResponse())
    }

    fun getProductsByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApi.getProducts(accountId.toAccountId())
            .map { it.toProductResponse() }

        call.respond(result)
    }

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApi.getInvoice(accountId.toAccountId())

        val invoice = InvoiceResponse(
            account = result.account.toAccountResponse(),
            products = result.products.map { it.toProductInvoiceResponse() },
            totalAmount = result.products.sumOf { it.price }.setScale(0)
        )

        call.respond(invoice)
    }

    data class AccountResponse(
        val accountId: String,
        val accountName: String
    )

    data class ProductResponse(
        val id: String,
        val productName: String
    )

    data class ProductInvoiceResponse(
        val id: String,
        val productName: String,
        val productPrice: BigDecimal
    )

    data class InvoiceResponse(
        val account: AccountResponse,
        val products: List<ProductInvoiceResponse>,
        val totalAmount: BigDecimal
    )

    private fun Account.toAccountResponse() = AccountResponse(
        accountId = id.id,
        accountName = name
    )

    private fun Product.toProductResponse() = ProductResponse(
        id = id.id,
        productName = name
    )

    private fun Product.toProductInvoiceResponse() = ProductInvoiceResponse(
        id = id.id,
        productName = name,
        productPrice = price
    )
}
