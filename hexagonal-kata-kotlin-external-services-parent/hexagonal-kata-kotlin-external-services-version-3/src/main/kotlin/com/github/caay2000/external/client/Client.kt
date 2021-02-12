package com.github.caay2000.external.client

import com.github.caay2000.external.data.Data.accountData

class Client {

    fun getAccountById(accountId: String): Account {

        if (accountData.containsKey(accountId)) {
            return accountData[accountId]!!
        }
        throw RuntimeException("Account not found")
    }

    data class Account(
        val accountId: String,
        val customerName: String,
        val customerProducts: List<String>
    )
}
