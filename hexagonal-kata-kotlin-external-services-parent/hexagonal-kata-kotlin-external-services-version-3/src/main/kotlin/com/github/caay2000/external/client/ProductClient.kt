package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountProductData
import com.github.caay2000.external.data.Data.productData
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductClientException

class ProductClient(configuration: ProductClientConfiguration) {

    fun getProductsByAccountId(accountId: String): List<Product> {

        if (accountProductData.containsKey(accountId)) {
            return accountProductData[accountId]!!.map {
                val data = productData[it]!!
                Product(
                    id = data.id,
                    name = data.name,
                    price = data.price,
                    premiumPrice = data.discountedPrice
                )
            }
        }
        throw ProductClientException()
    }
}
