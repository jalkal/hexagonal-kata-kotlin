package com.github.caay2000.application.api.outbound.port

import com.github.caay2000.application.api.model.Account
import com.github.caay2000.application.api.model.AccountId

interface AccountApi {

    fun getAccount(accountId: AccountId) : Account
}