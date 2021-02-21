package com.github.caay2000.application.api.outbound.port

import com.github.caay2000.application.api.model.AccountId
import com.github.caay2000.application.api.model.Product

interface ProductApi {

    fun getProducts(accountId: AccountId): List<Product>
}