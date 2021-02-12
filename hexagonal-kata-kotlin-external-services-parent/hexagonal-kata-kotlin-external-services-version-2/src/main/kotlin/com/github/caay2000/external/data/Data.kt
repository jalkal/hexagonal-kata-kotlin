package com.github.caay2000.external.data

import com.github.caay2000.external.client.Client

object Data {

    val accountData = mapOf(
        Pair("100", Client.Account("100", "", listOf("TV", "LAND LINE", "FIBRE"))),
        Pair("200", Client.Account("200", "", listOf("LAND LINE"))),
        Pair("300", Client.Account("300", "", listOf("TV"))),
        Pair("400", Client.Account("400", "", listOf("TV", "LAND LINE"))),
        Pair("500", Client.Account("500", "", listOf("TV", "LAND LINE", "FIBRE")))
    )
}
