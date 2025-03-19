package com.challenge.mocks

import io.ktor.server.config.*
import io.ktor.server.testing.*

fun ApplicationTestBuilder.createConfigMock() {
    environment {
        config = MapApplicationConfig(
            "ktor.environment" to "local",
            "ktor.config.local.accessToken" to "admin-access-token",
            "ktor.config.local.logLevel" to "INFO",
            "ktor.config.local.baseUrl" to "http://0.0.0.0:8080",
        )
    }
}