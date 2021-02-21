package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.repo.ProductRepository

class CustomerApplication(
    private val accountClient: AccountClient,
    private val productClient: ProductClient,
    private val productRepository: ProductRepository
) {

    fun getCustomerByAccountId(accountId: String): Account {

        return accountClient.getAccountById(accountId)
    }

    fun getProductsByAccountId(accountId: String): List<ProductResponse> {

        val listAccountProducts = productClient.getProductsByAccountId(accountId)
        val productInformation = productRepository.getProductInformation()

        return listAccountProducts.map {
            productInformation.first { data -> data.id == it }
        }.map {
            ProductResponse(it.id, it.productName)
        }
    }

    fun getInvoiceByAccountId(accountId: String): Invoice {

        val customer = accountClient.getAccountById(accountId)
        val listAccountProducts = productClient.getProductsByAccountId(accountId)
        val productInformation = productRepository.getProductInformation()

        val customerProducts = listAccountProducts.map {
            productInformation.first { data -> data.id == it }
        }

        return Invoice(
            customer = customer,
            products = customerProducts,
            totalAmount = customerProducts.map { it.productPrice }.sum()
        )
    }

    data class ProductResponse(
        val id: String,
        val productName: String
    )

    data class Invoice(
        val customer: Account,
        val products: List<Product>,
        val totalAmount: Int
    )
}
