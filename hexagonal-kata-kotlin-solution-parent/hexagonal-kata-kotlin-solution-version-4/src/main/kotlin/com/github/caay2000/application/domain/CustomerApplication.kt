package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.Product
import java.math.BigDecimal

class CustomerApplication(
    private val accountClient: AccountClient,
    private val productClient: ProductClient
) {

    fun getCustomerByAccountId(accountId: String): Account {

        return accountClient.getAccountById(accountId)
    }

    fun getProductsByAccountId(accountId: String): List<Product> = productClient.getProductsByAccountId(accountId)

    fun getInvoiceByAccountId(accountId: String): Invoice {

        val customer = accountClient.getAccountById(accountId)
        val customerProducts = getProductsByAccountId(accountId)

        return Invoice(
            customer = customer,
            products = customerProducts.map {
                InvoiceProduct(
                    id = it.id,
                    name = it.name,
                    price = getPrice(it, customer, customerProducts)
                )
            },
            totalAmount = customerProducts.map {
                if (customer.premiumCustomer) {
                    if (customerProducts.size == 4) {
                        it.premiumPrice.toBigDecimal() * BigDecimal(0.85)
                    } else {
                        it.premiumPrice.toBigDecimal()
                    }
                } else {
                    it.price.toBigDecimal()
                }
            }.fold(BigDecimal.ZERO, BigDecimal::add)
        )
    }

    private fun getPrice(it: Product, customer: Account, customerProducts: List<Product>): BigDecimal {
        return if (customer.premiumCustomer) {
            if (customerProducts.size == 4) {
                it.premiumPrice.toBigDecimal().multiply(BigDecimal(0.85))
            } else {
                it.premiumPrice.toBigDecimal()
            }
        } else {
            it.price.toBigDecimal()
        }
    }

    data class Invoice(
        val customer: Account,
        val products: List<InvoiceProduct>,
        val totalAmount: BigDecimal
    )

    data class InvoiceProduct(
        val id: String,
        val name: String,
        val price: BigDecimal
    )
}
