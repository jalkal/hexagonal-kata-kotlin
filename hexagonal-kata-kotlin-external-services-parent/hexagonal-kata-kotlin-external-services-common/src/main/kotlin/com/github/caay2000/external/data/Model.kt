package com.github.caay2000.external.model

import java.time.LocalDate

data class ProductData(
    val id: String,
    val name: String,
    val price: Int,
    val discountedPrice: Int
)

data class AccountData(
    val id: String,
    val name: String,
    val address: String,
    val city: String,
    val postalCode: String,
    val email: String,
    val birthDate: LocalDate
)
