package com.github.caay2000.application.domain

import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId
import com.github.caay2000.application.api.model.Invoice
import com.github.caay2000.application.api.model.Product
import com.github.caay2000.application.api.internal.ProductApi
import com.github.caay2000.application.api.external.AccountApi as ExternalAccountApi
import com.github.caay2000.application.api.internal.AccountApi as InternalAccountApi

class AccountApplication(private val accountApi: InternalAccountApi, private val productApi: ProductApi) : ExternalAccountApi {
    override fun getAccount(accountId: AccountId): Account {
        return accountApi.getAccount(accountId)
    }

    override fun getProducts(accountId: AccountId): List<Product> {
        return productApi.getProducts(accountId)
    }

    override fun getInvoice(accountId: AccountId): Invoice {
        val account = this.getAccount(accountId)
        val products = this.getProducts(accountId)

        return Invoice(
            account,
            products,
            products.sumOf { it.price }
        )
    }
}