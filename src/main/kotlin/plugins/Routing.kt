package com.challenge.plugins

import com.challenge.repositories.FactRepository
import com.challenge.routes.adminRoutes
import com.challenge.routes.factsRoutes
import io.ktor.client.*
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRoutes(client: HttpClient, repository: FactRepository) {
    routing {
        adminRoutes(repository)
        factsRoutes(repository, client)
    }
}
