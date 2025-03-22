package com.challenge.routes

import com.challenge.model.Fact
import com.challenge.repositories.FactRepository
import com.challenge.resources.Facts
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.request.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route

/**
 * Defines routes for managing facts.
 *
 * - Allows retrieving and adding facts.
 * - Supports fetching random facts from an external API.
 *
 * @param repository The repository instance for storing and retrieving facts.
 * @param client The HTTP client used for fetching external data.
 */
fun Route.factsRoutes(repository: FactRepository, client: HttpClient) {

    /**
     * Retrieves a paginated list of stored facts.
     *
     * **Request:**
     * - Supports pagination via `offset` and `limit` parameters.
     *
     * **Response:**
     * - Returns a paginated list of facts.
     */
    get<Facts> { params ->
        call.respond(repository.getFacts(params.offset, params.limit))
    }

    /**
     * Fetches a random fact from an external API and stores it.
     *
     * **External API:**
     * - Calls `https://uselessfacts.jsph.pl/api/v2/facts/random` to retrieve a random fact.
     *
     * **Response:**
     * - Stores the fetched fact in the repository.
     * - Returns the newly stored fact.
     */
    post<Facts> {
        val response = client.get("https://uselessfacts.jsph.pl/api/v2/facts/random")
        val randomFact = response.body<Fact>()
        repository.addFact(randomFact)
        call.respond(randomFact)
    }

    /**
     * Retrieves a specific fact by its short ID.
     *
     * **Request:**
     * - Requires a valid `shortId` parameter.
     *
     * **Response:**
     * - Returns the corresponding fact if found.
     * - Responds with an error if the fact does not exist.
     */
    get<Facts.ShortId> { params ->
        call.respond(repository.getFact(params.shortId))
    }
}