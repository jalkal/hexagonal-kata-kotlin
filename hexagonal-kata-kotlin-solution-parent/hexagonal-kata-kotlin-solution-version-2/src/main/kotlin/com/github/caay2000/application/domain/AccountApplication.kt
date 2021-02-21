package com.github.caay2000.application.domain

import com.github.caay2000.application.api.external.Invoice
import com.github.caay2000.application.api.external.InvoiceProduct
import com.github.caay2000.application.api.external.Product
import com.github.caay2000.application.api.internal.ProductApi
import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId
import java.math.BigDecimal
import com.github.caay2000.application.api.external.AccountApi as ExternalAccountApi
import com.github.caay2000.application.api.internal.AccountApi as InternalAccountApi

class AccountApplication(private val accountApi: InternalAccountApi, private val productApi: ProductApi) : ExternalAccountApi {
    override fun getAccount(accountId: AccountId): Account {
        return accountApi.getAccount(accountId)
    }

    override fun getProducts(accountId: AccountId): List<Product> {
        return productApi.getProducts(accountId).map {
            Product(
                id = it.id,
                name = it.name
            )
        }
    }

    override fun getInvoice(accountId: AccountId): Invoice {
        val account = accountApi.getAccount(accountId)

        val products = productApi.getProducts(accountId)
            .map {
                InvoiceProduct(
                    id = it.id,
                    name = it.name,
                    price = it.calculatePrice(account.isPremium)
                )
            }

        return Invoice(
            account,
            products,
            products.sumOf { it.price }
        )
    }

    private fun com.github.caay2000.application.api.internal.Product.calculatePrice(isPremium: Boolean): BigDecimal =
        if (isPremium) premiumPrice else price
}


