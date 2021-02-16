package com.github.caay2000.application.infrastructure.http

import com.github.caay2000.application.domain.CustomerApplication
import io.ktor.application.ApplicationCall
import io.ktor.application.call
import io.ktor.response.respond
import io.ktor.util.pipeline.PipelineContext
import java.math.BigDecimal

class CustomerController(private val customerApplication: CustomerApplication) {

    fun getCustomerByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getCustomerByAccountId(accountId)

        call.respond(CustomerResponse(
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

        val result = customerApplication.getProductsByAccountId(accountId).map {
            ProductResponse(
                    id = it.id,
                    name = it.name,
                    price = it.price.toBigDecimal().setScale(2)
            )
        }

        call.respond(result)
    }

    fun getInvoiceByAccountId(): suspend PipelineContext<Unit, ApplicationCall>.(Unit) -> Unit = {

        val accountId = call.parameters["accountId"] ?: throw IllegalArgumentException("parameter accountId not found")

        val result = customerApplication.getInvoiceByAccountId(accountId)

        call.respond(InvoiceResponse(
                customer = CustomerResponse(
                        accountId = result.customer.accountId,
                        name = result.customer.name,
                        address = AddressResponse(
                                addressLine = result.customer.address,
                                city = result.customer.city,
                                postalCode = result.customer.postalCode
                        ),
                        email = result.customer.email
                ),
                products = result.products.map {
                    ProductResponse(
                            id = it.id,
                            name = it.name,
                            price = it.price.toBigDecimal().setScale(2)
                    )
                },
                totalAmount = result.totalAmount.toBigDecimal().setScale(2)
        ))
    }

    data class CustomerResponse(
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

    data class ProductResponse(
            val id: String,
            val name: String,
            val price: BigDecimal
    )

    data class InvoiceResponse(
            val customer: CustomerResponse,
            val products: List<ProductResponse>,
            val totalAmount: BigDecimal
    )
}
