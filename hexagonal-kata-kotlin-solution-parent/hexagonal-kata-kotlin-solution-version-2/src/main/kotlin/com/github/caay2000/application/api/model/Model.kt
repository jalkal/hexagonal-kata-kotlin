package com.github.caay2000.application.api.model

data class AccountId(val id: String)

fun String.toAccountId() = AccountId(this)

data class ProductId(val id: String)

fun String.toProductId() = ProductId(this)

data class Account(
    val id: AccountId,
    val name: String,
    val address: Address,
    val email: String,
    val isPremium: Boolean
)

data class Address(
    val addressLine: String,
    val city: String,
    val postalCode: String
)