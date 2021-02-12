package com.github.caay2000.application.domain

import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.model.Account

class CustomerApplication(private val accountClient: AccountClient) {

    fun getProductsByAccountId(accountId: String): Account {

        return accountClient.getAccountById(accountId)
    }
}
