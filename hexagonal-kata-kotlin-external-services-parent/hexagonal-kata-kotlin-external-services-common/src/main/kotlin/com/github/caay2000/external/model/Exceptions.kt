package com.github.caay2000.external.model

sealed class ExternalException : Throwable() {
    abstract override val message: String
}

sealed class ClientException(override val message: String) : ExternalException()
data class AccountClientException(override val message: String = "Account Client Exception") : ClientException(message)
data class ProductClientException(override val message: String = "Product Client Exception") : ClientException(message)

sealed class RepositoryException(override val message: String) : ExternalException()
data class ProductRepositoryException(override val message: String = "Product Repository Exception") : RepositoryException(message)
