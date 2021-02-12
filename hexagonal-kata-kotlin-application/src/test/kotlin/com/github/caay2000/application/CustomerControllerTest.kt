package com.github.caay2000.application

import com.github.caay2000.application.infrastructure.main
import io.ktor.application.Application
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.withTestApplication
import io.ktor.util.KtorExperimentalAPI
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

@KtorExperimentalAPI
class CustomerControllerTest {

    @CsvSource(
        "8461913, Constance Middleton",
        "8267017, Ivory Ayers",
        "8301466, Christen Conrad",
        "8729755, Silas Mack",
        "8069311, Uriel Torres"
    )
    @ParameterizedTest
    fun `controller should return correct json`(accountId: String, customerName: String) {
        withTestApplication(Application::main) {

            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/customer/${accountId}"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.response.content).isEqualToIgnoringWhitespace(
                """{
                  "accountId": "$accountId",
                  "customerName": "$customerName"
                }""".trimIndent()
            )
        }
    }
}
