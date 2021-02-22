package com.github.caay2000.application

import com.github.caay2000.application.infrastructure.main
import io.ktor.application.Application
import io.ktor.http.HttpStatusCode
import io.ktor.server.testing.withTestApplication
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvSource

class AccountControllerTest {

    @Test
    fun `controller should return exception if accountId is invalid for account information`() {
        withTestApplication(Application::main) {

            val accountId = "invalid"
            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/account/${accountId}"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.InternalServerError)
        }
    }

    @CsvSource("8069311", "8267017", "8301466", "8461913", "8535077", "8651711", "8729755", "8740957", "8832953")
    @ParameterizedTest
    fun `controller should return correct account json for accountId`(accountId: String) {
        withTestApplication(Application::main) {

            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/account/${accountId}"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.response.content).isEqualToIgnoringWhitespace(getResultResource("account", accountId))
        }
    }

    @Test
    fun `controller should return exception if accountId is invalid for product information`() {
        withTestApplication(Application::main) {

            val accountId = "invalid"
            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/account/${accountId}/products"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.InternalServerError)
        }
    }

    @CsvSource("8069311", "8267017", "8301466", "8461913", "8535077", "8651711", "8729755", "8740957", "8832953")
    @ParameterizedTest
    fun `controller should return correct product json for accountId`(accountId: String) {
        withTestApplication(Application::main) {

            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/account/${accountId}/products"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.response.content).isEqualToIgnoringWhitespace(getResultResource("product", accountId))
        }
    }

    @Test
    fun `controller should return exception if accountId is invalid for invoice information`() {
        withTestApplication(Application::main) {

            val accountId = "invalid"
            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/account/${accountId}/invoice"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.InternalServerError)
        }
    }

    @CsvSource("8069311", "8267017", "8301466", "8461913", "8535077", "8651711", "8729755", "8740957", "8832953")
    @ParameterizedTest
    fun `controller should return correct invoice json for accountId`(accountId: String) {
        withTestApplication(Application::main) {

            val response = handleRequest {
                method = io.ktor.http.HttpMethod.Get
                uri = "/account/${accountId}/invoice"
            }

            assertThat(response.response.status()).isEqualTo(HttpStatusCode.OK)
            assertThat(response.response.content).isEqualToIgnoringWhitespace(getResultResource("invoice", accountId))
        }
    }

    private fun getResultResource(type: String, id: String): String {
        return object {}.javaClass.getResource("/results/$type/$id.json").readText()
    }
}