package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountData
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.AccountClientConfiguration
import com.github.caay2000.external.model.AccountClientException

class AccountClient(configuration: AccountClientConfiguration) {

    fun getAccountById(accountId: String): Account {

        if (accountData.containsKey(accountId)) {
            val data = accountData[accountId]!!
            return Account(
                accountId = data.id,
                name = data.name,
                address = data.address,
                city = data.city,
                postalCode = data.postalCode,
                email = data.email,
                birthDate = data.birthDate,
                premiumAccount = data.premiumAccount
            )
        }
        throw AccountClientException()
    }
}
