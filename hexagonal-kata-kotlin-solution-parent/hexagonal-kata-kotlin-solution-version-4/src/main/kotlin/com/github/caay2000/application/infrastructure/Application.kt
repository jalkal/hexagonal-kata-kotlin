package com.github.caay2000.application.infrastructure

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.databind.SerializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.github.caay2000.application.domain.CustomerApplication
import com.github.caay2000.application.infrastructure.http.CustomerController
import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.client.ProductClient
import com.github.caay2000.external.model.AccountClientConfiguration
import com.github.caay2000.external.model.ProductClientConfiguration
import com.github.caay2000.external.model.ProductRepositoryConfiguration
import io.ktor.application.Application
import io.ktor.application.call
import io.ktor.application.install
import io.ktor.features.ContentNegotiation
import io.ktor.features.StatusPages
import io.ktor.http.HttpStatusCode
import io.ktor.jackson.jackson
import io.ktor.response.respond
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get

@KtorExperimentalAPI
fun Application.main() {

    install(Koin) {
        modules(module {

            single { AccountClientConfiguration() }
            single { ProductClientConfiguration() }
            single { ProductRepositoryConfiguration() }

            single { AccountClient(configuration = get()) }
            single { ProductClient(configuration = get()) }

            single {
                CustomerApplication(
                    accountClient = get(),
                    productClient = get()
                )
            }
            single { CustomerController(customerApplication = get()) }
        })
    }

    install(StatusPages) {
        exception<Throwable> { call.respond(HttpStatusCode.InternalServerError) }
    }

    install(ContentNegotiation) {
        jackson {
            enable(SerializationFeature.INDENT_OUTPUT)
            disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS)
            registerModule(JavaTimeModule())
            setSerializationInclusion(JsonInclude.Include.NON_NULL)
        }
    }

    routing {
        get("/customer/{accountId}", get<CustomerController>().getCustomerByAccountId())
        get("/customer/{accountId}/products", get<CustomerController>().getProductsByAccountId())
        get("/customer/{accountId}/invoice", get<CustomerController>().getInvoiceByAccountId())
    }
}