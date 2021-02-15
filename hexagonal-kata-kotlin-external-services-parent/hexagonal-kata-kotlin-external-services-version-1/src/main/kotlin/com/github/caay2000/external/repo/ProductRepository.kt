package com.github.caay2000.external.repo

import com.github.caay2000.external.data.Data.productData
import com.github.caay2000.external.model.ProductData
import com.github.caay2000.external.model.ProductRepositoryConfiguration

class ProductRepository(productRepositoryConfiguration: ProductRepositoryConfiguration) {

    fun getProductInformation(): List<ProductData> = productData.map { it.value }
}
