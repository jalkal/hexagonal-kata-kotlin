package com.github.caay2000.application.infrastructure.adapter

import com.github.caay2000.application.api.internal.port.AccountApi
import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId
import com.github.caay2000.application.api.model.toAccountId
import com.github.caay2000.external.client.AccountClient

class AccountAdapter(private val accountClient: AccountClient) : AccountApi {

    override fun getAccount(accountId: AccountId): Account {

        val customer = accountClient.getAccountById(accountId.id)

        return Account(
            id = customer.accountId.toAccountId(),
            name = customer.customerName
        )
    }
}