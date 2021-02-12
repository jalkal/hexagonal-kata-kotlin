package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountData
import com.github.caay2000.external.model.Account
import com.github.caay2000.external.model.AccountClientConfiguration
import com.github.caay2000.external.model.AccountClientException

class AccountClient(private val configuration: AccountClientConfiguration) {

    fun getAccountById(accountId: String): Account {

        if (accountData.containsKey(accountId)) {
            val data = accountData[accountId]!!
            return Account(data.id, data.name)
        }
        throw AccountClientException()
    }
}
