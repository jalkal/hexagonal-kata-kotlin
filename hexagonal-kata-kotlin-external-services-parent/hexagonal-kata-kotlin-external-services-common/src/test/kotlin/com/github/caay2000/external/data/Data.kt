package com.github.caay2000.external.data

import com.github.caay2000.external.data.Data.accountProductData
import com.github.caay2000.external.data.Data.productData
import com.github.caay2000.external.model.ProductData

class DataUtils {

    fun getProductDataByAccountId(accountId: String): List<ProductData> {

        return accountProductData[accountId]!!.map {
            productData[it]!!
        }
    }
}