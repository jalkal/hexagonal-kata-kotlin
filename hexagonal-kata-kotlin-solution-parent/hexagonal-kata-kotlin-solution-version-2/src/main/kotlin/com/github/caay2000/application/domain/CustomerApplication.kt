package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.repo.ProductRepository

class AccountApplication(
    private val accountClient: AccountClient,
    private val productClient: ProductClient,
    private val productRepository: ProductRepository
) {

    fun getAccountByAccountId(accountId: String): Account {

        return accountClient.getAccountById(accountId)
    }

    fun getProductsByAccountId(accountId: String): List<Product> {

        val listAccountProducts = productClient.getProductsByAccountId(accountId)
        val productInformation = productRepository.getProductInformation()

        return listAccountProducts.products.map {
            productInformation.first { data -> data.id == it }
        }
    }

    fun getInvoiceByAccountId(accountId: String): Invoice {

        val account = accountClient.getAccountById(accountId)
        val accountProducts = getProductsByAccountId(accountId)

        return Invoice(
            account = account,
            products = accountProducts.map {
                if (account.premiumAccount) {
                    it.copy(price = it.premiumPrice)
                } else {
                    it
                }
            },
            totalAmount = accountProducts.map {
                if (account.premiumAccount) {
                    if(accountProducts.size == 4){
                        it.premiumPrice
                    } else {
                        it.premiumPrice
                    }
                } else {
                    it.price
                }
            }.sum()
        )
    }

    data class Invoice(
        val account: Account,
        val products: List<Product>,
        val totalAmount: Int
    )
}
