package com.github.caay2000.application

import com.github.caay2000.application.infrastructure.CustomerController
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class CustomerControllerTest {

    private val sut = CustomerController()

    @CsvSource(
        "8461913, Constance Middleton",
        "8267017, Ivory Ayers",
        "8301466, Christen Conrad",
        "8729755, Silas Mack",
        "8069311, Uriel Torres"
    )
    @ParameterizedTest
    fun `controller should return correct json`(accountId: String, customerName: String) {

        val result = sut.getCustomerByAccountId(accountId)

        assertThat(result).isEqualToIgnoringWhitespace(
            """{
              "accountId": "$accountId",
              "customerName": "$customerName"
            }""".trimIndent()
        )
    }
}
