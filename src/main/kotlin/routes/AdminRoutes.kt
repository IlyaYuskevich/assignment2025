package com.challenge.routes

import com.challenge.repositories.FactRepository
import com.challenge.resources.Admin
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.adminRoutes(repository: FactRepository) {
    authenticate("bearer-token") {
        get<Admin.Statistics> { params ->
            call.respond(repository.getStatistics(params.offset, params.limit, params.order))
        }
    }
}