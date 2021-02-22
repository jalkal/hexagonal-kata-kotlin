package com.github.caay2000.application.infrastructure.adapter

import com.github.caay2000.application.api.internal.Product
import com.github.caay2000.application.api.internal.ProductApi
import com.github.caay2000.application.api.model.AccountId
import com.github.caay2000.application.api.model.toProductId
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.repo.ProductRepository

class ProductAdapter(private val productClient: ProductClient, private val productRepository: ProductRepository) : ProductApi {

    override fun getProducts(accountId: AccountId): List<Product> {

        val customerProducts = productClient.getProductsByAccountId(accountId.id)
        val products = productRepository.getProductInformation()

        return customerProducts.products
            .map {
                products.first { data -> data.id == it }
            }.map {
                Product(
                    id = it.id.toProductId(),
                    name = it.name,
                    price = it.price.toBigDecimal(),
                    premiumPrice = it.premiumPrice.toBigDecimal()
                )
            }
    }
}