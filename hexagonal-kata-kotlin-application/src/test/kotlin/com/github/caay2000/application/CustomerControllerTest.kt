package com.github.caay2000.application

import com.github.caay2000.application.infrastructure.main
import io.ktor.application.Application
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.Arguments
import org.junit.jupiter.params.provider.CsvSource
import org.junit.jupiter.params.provider.MethodSource
import java.util.stream.Stream

class CustomerControllerTest {

    @CsvSource(
        "8461913, Constance Middleton",
        "8267017, Ivory Ayers",
        "8301466, Christen Conrad",
        "8729755, Silas Mack",
        "8069311, Uriel Torres"
    )
    @ParameterizedTest
    fun `controller should return correct customer json for accountId`(accountId: String, customerName: String) {
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

    @MethodSource("productTestDataByAccountId")
    @ParameterizedTest
    fun `controller should return correct product json for accountId`(accountId: String, result: String) {
        withTestApplication(Application::main) {

            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/customer/${accountId}/products"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.response.content).isEqualToIgnoringWhitespace(result)

        }
    }

    @Test
    fun `controller should return exception if accountId is invalid for customer information`() {
        withTestApplication(Application::main) {

            val accountId = "invalid"
            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/customer/${accountId}"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.InternalServerError)
        }
    }

    @Test
    fun `controller should return exception if accountId is invalid for product information`() {
        withTestApplication(Application::main) {

            val accountId = "invalid"
            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/customer/${accountId}/products"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.InternalServerError)
        }
    }

    @Suppress("unused")
    fun productTestDataByAccountId(): Stream<Arguments> = Stream.of(
        Arguments.of("8535077", "[]"),
        Arguments.of("8740957", """[{ "id": 120, "name": "TV PREMIUM", "price": 120 }]"""),
        Arguments.of("8651711", """[{ "id": 100, "name": "TV", "price": 80 }, { "id": 200, "name": "LAND LINE", "price": 20 }]""")
    )
}
