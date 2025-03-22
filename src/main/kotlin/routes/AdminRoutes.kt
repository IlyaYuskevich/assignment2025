package com.challenge.routes

import com.challenge.repositories.FactRepository
import com.challenge.resources.Admin
import io.ktor.server.auth.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route

/**
 * Defines routes for admin-related operations.
 *
 * - Requires authentication using the "bearer-token" scheme.
 * - Provides an endpoint to retrieve fact access statistics.
 *
 * @param repository The repository instance for accessing fact data.
 */
fun Route.adminRoutes(repository: FactRepository) {
    authenticate("bearer-token") {
        /**
         * Retrieves fact access statistics.
         *
         * **Request:**
         * - Protected by bearer token authentication.
         * - Accepts pagination parameters (`offset`, `limit`, `order`).
         *
         * **Response:**
         * - Returns paginated statistics of fact accesses.
         */
        get<Admin.Statistics> { params ->
            call.respond(repository.getStatistics(params.offset, params.limit, params.order))
        }
    }
}