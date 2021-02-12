package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.productData
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.model.ProductClientException

class ProductClient {

    fun getProductsByAccountId(accountId: String): Product {

        if (productData.containsKey(accountId)) {
            val data = productData[accountId]!!
            return Product(data.id, data.name)
        }
        throw ProductClientException()
    }
}
