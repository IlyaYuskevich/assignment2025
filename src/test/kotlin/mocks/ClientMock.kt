package com.challenge.mocks

import io.ktor.client.*
import io.ktor.client.plugins.contentnegotiation.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.serialization.kotlinx.json.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import io.ktor.server.testing.*

fun ApplicationTestBuilder.createTestClient(): HttpClient {
    val client = createClient {
        install(ContentNegotiation) {
            json()
        }
        install(Resources)
    }
    return client
}

fun ApplicationTestBuilder.configureExternalServices() {
    externalServices {
        val getRandomFact = getRandomFactFactory()
        hosts("https://uselessfacts.jsph.pl") {
            install(io.ktor.server.plugins.contentnegotiation.ContentNegotiation) {
                json()
            }
            routing {
                get("/api/v2/facts/random") {
                    call.respond(HttpStatusCode.OK, getRandomFact())
                }
            }
        }
    }
}