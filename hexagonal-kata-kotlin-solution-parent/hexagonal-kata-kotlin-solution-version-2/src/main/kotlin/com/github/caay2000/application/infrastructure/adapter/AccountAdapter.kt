package com.github.caay2000.application.infrastructure.adapter

import com.github.caay2000.application.api.internal.AccountApi
import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId
import com.github.caay2000.application.api.model.Address
import com.github.caay2000.application.api.model.toAccountId
import com.github.caay2000.external.client.AccountClient

class AccountAdapter(private val accountClient: AccountClient) : AccountApi {

    override fun getAccount(accountId: AccountId): Account {

        val customer = accountClient.getAccountById(accountId.id)
        return customer.toAccount()
    }

    private fun com.github.caay2000.external.model.Account.toAccount(): Account =
        Account(
            id = this.accountId.toAccountId(),
            name = this.name,
            address = Address(
                addressLine = this.address,
                city = this.city,
                postalCode = this.postalCode
            ),
            email = this.email,
            isPremium = this.premiumCustomer
        )
}