package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.model.Account

class CustomerApplication {

    private val customerClient = AccountClient()

    fun getProductsByAccountId(accountId: String): Account {

        return customerClient.getAccountById(accountId)
    }
}
