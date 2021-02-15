package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountProductData
import com.github.caay2000.external.model.AccountProduct
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductClientException

class ProductClient(configuration: ProductClientConfiguration) {

    fun getProductsByAccountId(accountId: String): AccountProduct {

        if (accountProductData.containsKey(accountId)) {
            return AccountProduct(accountProductData[accountId]!!)
        }
        throw ProductClientException()
    }
}
