package com.github.caay2000.application.api.external

import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.ProductId
import java.math.BigDecimal

data class Product(
    val id: ProductId,
    val name: String
)

data class InvoiceProduct(
    val id: ProductId,
    val name: String,
    val price: BigDecimal
)

data class Invoice(
    val account: Account,
    val products: List<InvoiceProduct>,
    val totalInvoice: BigDecimal
)
