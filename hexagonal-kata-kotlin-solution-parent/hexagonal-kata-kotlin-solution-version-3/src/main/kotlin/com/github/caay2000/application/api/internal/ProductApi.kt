package com.github.caay2000.application.api.internal

import com.github.caay2000.application.api.model.AccountId

interface ProductApi {

    fun getProducts(accountId: AccountId): List<Product>
}