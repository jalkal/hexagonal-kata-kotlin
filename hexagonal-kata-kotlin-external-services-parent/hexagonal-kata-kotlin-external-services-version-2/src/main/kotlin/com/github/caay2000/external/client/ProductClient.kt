package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountProductData
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductClientException

class ProductClient(private val configuration: ProductClientConfiguration) {

    fun getProductsByAccountId(accountId: String): List<String> {

        if (accountProductData.containsKey(accountId)) {
            return accountProductData[accountId]!!
        }
        throw ProductClientException()
    }
}
