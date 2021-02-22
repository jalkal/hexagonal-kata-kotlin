package com.github.caay2000.application.domain

import com.github.caay2000.application.api.external.Invoice
import com.github.caay2000.application.api.external.InvoiceProduct
import com.github.caay2000.application.api.external.Product
import com.github.caay2000.application.api.internal.ProductApi
import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId
import java.math.BigDecimal
import java.math.RoundingMode
import com.github.caay2000.application.api.external.AccountApi as ExternalAccountApi
import com.github.caay2000.application.api.internal.AccountApi as InternalAccountApi
import com.github.caay2000.application.api.internal.Product as InternalProduct

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
        return Invoice(
            account,
            products.map {
                InvoiceProduct(
                    id = it.id,
                    name = it.name,
                    price = it.calculatePrice(account, products)
                )
            },
            products.sumOf { it.price }
        )
    }

    private fun InternalProduct.calculatePrice(account: Account, products: List<InternalProduct>): BigDecimal =
        when {
            account.isPremium && products.size == 4 -> premiumPrice.multiply(BigDecimal(0.85)).setScale(2, RoundingMode.UP)
            account.isPremium && products.size != 4 -> premiumPrice.setScale(2, RoundingMode.UP)
            else -> price
        }
}


