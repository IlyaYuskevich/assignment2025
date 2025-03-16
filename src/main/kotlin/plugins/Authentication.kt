package com.challenge.plugins

import io.ktor.server.application.*
import io.ktor.server.auth.*

fun Application.configureSecurity(accessToken: String) {
    install(Authentication) {
        bearer("bearer-token") {
            realm = "Access to the /admin path"
            authenticate { bearerTokenCredential ->
                if (bearerTokenCredential.token == accessToken) {
                    UserIdPrincipal("admin")
                } else {
                    null
                }
            }
        }
    }
}