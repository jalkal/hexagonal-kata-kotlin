package com.github.caay2000.external.repo

import com.github.caay2000.external.data.Data.productData
import com.github.caay2000.external.model.Product
import com.github.caay2000.external.model.ProductRepositoryConfiguration

class ProductRepository(configuration: ProductRepositoryConfiguration) {

    fun getProductInformation(): List<Product> =
        productData.map {
            Product(
                id = it.value.id,
                name = it.value.name,
                price = it.value.price,
                premiumPrice = it.value.discountedPrice
            )
        }
}
