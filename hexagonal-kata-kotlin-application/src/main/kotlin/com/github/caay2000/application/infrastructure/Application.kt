package com.github.caay2000.application.infrastructure

import com.github.caay2000.application.domain.CustomerApplication
import com.github.caay2000.application.infrastructure.http.CustomerController
import com.github.caay2000.external.client.AccountClient
import com.github.caay2000.external.model.AccountClientConfiguration
import com.github.caay2000.external.model.ProductClientConfiguration
import io.ktor.application.Application
import io.ktor.application.install
import io.ktor.features.CallLogging
import io.ktor.features.DefaultHeaders
import io.ktor.routing.get
import io.ktor.routing.routing
import io.ktor.util.KtorExperimentalAPI
import org.koin.dsl.module
import org.koin.ktor.ext.Koin
import org.koin.ktor.ext.get

@KtorExperimentalAPI
fun Application.main() {

    install(DefaultHeaders)
    install(CallLogging)
    install(Koin) {
        modules(koinModules)
    }

    routing {
        get("/customer/{accountId}", get<CustomerController>().getCustomerByAccountId())
    }
}

val koinModules = module {

    single { AccountClientConfiguration() }
    single { ProductClientConfiguration() }

    single { AccountClient(get()) }

    single { CustomerApplication(get()) }
    single { CustomerController(get()) }
}
