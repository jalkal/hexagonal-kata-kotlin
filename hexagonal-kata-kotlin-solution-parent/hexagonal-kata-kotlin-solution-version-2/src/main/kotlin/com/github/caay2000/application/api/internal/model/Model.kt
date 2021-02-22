package com.github.caay2000.application.api.internal

import com.github.caay2000.application.api.model.ProductId
import java.math.BigDecimal

data class Product(
    val id: ProductId,
    val name: String,
    val price: BigDecimal,
    val premiumPrice: BigDecimal
)