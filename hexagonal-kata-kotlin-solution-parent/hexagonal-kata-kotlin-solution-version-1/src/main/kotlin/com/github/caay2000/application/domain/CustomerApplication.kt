package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.ProductData
import com.github.caay2000.external.repo.ProductRepository

class CustomerApplication(
    private val accountClient: AccountClient,
    private val productClient: ProductClient,
    private val productRepository: ProductRepository
) {

    fun getCustomerByAccountId(accountId: String): Account {

        return accountClient.getAccountById(accountId)
    }

    fun getProductsByAccountId(accountId: String): List<ProductData> {

        val listAccountProducts = productClient.getProductsByAccountId(accountId)
        val productInformation = productRepository.getProductInformation()

        return listAccountProducts.map {
            productInformation.first { data -> data.id == it }
        }
    }

    fun getInvoiceByAccountId(accountId: String): Invoice {

        val customer = accountClient.getAccountById(accountId)
        val customerProducts = getProductsByAccountId(accountId)

        return Invoice(
            customer = customer,
            products = customerProducts,
            totalAmount = customerProducts.map { it.price }.sum()
        )
    }

    data class Invoice(
        val customer: Account,
        val products: List<ProductData>,
        val totalAmount: Int
    )
}
