package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.Product
import java.math.BigDecimal

class AccountApplication(
    private val accountClient: AccountClient,
    private val productClient: ProductClient
) {

    fun getAccountByAccountId(accountId: String): Account {

        return accountClient.getAccountById(accountId)
    }

    fun getProductsByAccountId(accountId: String): List<Product> = productClient.getProductsByAccountId(accountId)

    fun getInvoiceByAccountId(accountId: String): Invoice {

        val account = accountClient.getAccountById(accountId)
        val accountProducts = getProductsByAccountId(accountId)

        return Invoice(
            account = account,
            products = accountProducts.map {
                InvoiceProduct(
                    id = it.id,
                    name = it.name,
                    price = getPrice(it, account, accountProducts)
                )
            },
            totalAmount = accountProducts.map {
                if (account.premiumAccount) {
                    if (accountProducts.size == 4) {
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

    private fun getPrice(it: Product, account: Account, accountProducts: List<Product>): BigDecimal {
        return if (account.premiumAccount) {
            if (accountProducts.size == 4) {
                it.premiumPrice.toBigDecimal().multiply(BigDecimal(0.85))
            } else {
                it.premiumPrice.toBigDecimal()
            }
        } else {
            it.price.toBigDecimal()
        }
    }

    data class Invoice(
        val account: Account,
        val products: List<InvoiceProduct>,
        val totalAmount: BigDecimal
    )

    data class InvoiceProduct(
        val id: String,
        val name: String,
        val price: BigDecimal
    )
}
