package com.challenge.plugins

import com.challenge.client
import com.challenge.routes.adminRoutes
import com.challenge.routes.factsRoutes
import com.challenge.storage.InMemoryFactRepository
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes() {
    routing {
        adminRoutes(InMemoryFactRepository)
        factsRoutes(InMemoryFactRepository, client)
    }
}
