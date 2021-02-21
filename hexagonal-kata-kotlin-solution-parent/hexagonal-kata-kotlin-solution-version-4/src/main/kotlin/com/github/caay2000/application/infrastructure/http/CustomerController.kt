package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.AccountApplication
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.math.BigDecimal
import java.math.RoundingMode

class AccountController(private val accountApplication: AccountApplication) {

    fun getAccountByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApplication.getAccountByAccountId(accountId)

        call.respond(AccountResponse(
                accountId = result.accountId,
                name = result.name,
                address = AddressResponse(
                        addressLine = result.address,
                        city = result.city,
                        postalCode = result.postalCode
                ),
                email = result.email
        ))
    }

    fun getProductsByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApplication.getProductsByAccountId(accountId).map {
            ProductNoPriceResponse(
                    id = it.id,
                    name = it.name
            )
        }

        call.respond(result)
    }

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = accountApplication.getInvoiceByAccountId(accountId)

        call.respond(InvoiceResponse(
                account = AccountResponse(
                        accountId = result.account.accountId,
                        name = result.account.name,
                        address = AddressResponse(
                                addressLine = result.account.address,
                                city = result.account.city,
                                postalCode = result.account.postalCode
                        ),
                        email = result.account.email
                ),
                products = result.products.map {
                    ProductResponse(
                            id = it.id,
                            name = it.name,
                            price = it.price.setScale(2, RoundingMode.HALF_UP)
                    )
                },
                totalAmount = result.totalAmount.setScale(2, RoundingMode.HALF_UP)
        ))
    }

    data class AccountResponse(
            val accountId: String,
            val name: String,
            val address: AddressResponse,
            val email: String
    )

    data class AddressResponse(
            val addressLine: String,
            val city: String,
            val postalCode: String
    )

    data class ProductNoPriceResponse(
        val id: String,
        val name: String
    )

    data class ProductResponse(
            val id: String,
            val name: String,
            val price: BigDecimal
    )

    data class InvoiceResponse(
            val account: AccountResponse,
            val products: List<ProductResponse>,
            val totalAmount: BigDecimal
    )
}
