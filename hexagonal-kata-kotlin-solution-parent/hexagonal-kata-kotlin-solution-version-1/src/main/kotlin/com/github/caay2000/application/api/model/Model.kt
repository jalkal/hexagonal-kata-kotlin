package com.github.caay2000.application.api.model

import java.math.BigDecimal

data class AccountId(val id: String)
fun String.toAccountId() = AccountId(this)

data class ProductId(val id: String)
fun String.toProductId() = ProductId(this)

data class Account(
    val id: AccountId,
    val name: String
)

data class Product(
    val id: ProductId,
    val name: String,
    val price: BigDecimal
)

data class Invoice(
    val account: Account,
    val products: List<Product>,
    val totalInvoice: BigDecimal
)