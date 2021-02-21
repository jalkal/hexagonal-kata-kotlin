package com.github.caay2000.application.api.external.port

import com.github.caay2000.application.api.model.AccountId
import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.Invoice
import com.github.caay2000.application.api.model.Product

interface AccountApi {

    fun getAccount(accountId: AccountId) : Account
    fun getProducts(accountId: AccountId) : List<Product>
    fun getInvoice(accountId: AccountId) : Invoice
}