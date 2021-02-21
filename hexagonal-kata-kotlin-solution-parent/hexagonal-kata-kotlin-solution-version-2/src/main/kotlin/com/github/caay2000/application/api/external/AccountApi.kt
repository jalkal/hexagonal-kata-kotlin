package com.github.caay2000.application.api.external

import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId

interface AccountApi {

    fun getAccount(accountId: AccountId): Account
    fun getProducts(accountId: AccountId): List<Product>
    fun getInvoice(accountId: AccountId): Invoice
}