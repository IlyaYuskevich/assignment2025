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

fun Route.factsRoutes(repository: FactRepository, client: HttpClient) {
    get<Facts> { params ->
        call.respond(repository.getFacts(params.offset, params.limit))
    }

    post<Facts> {
        val response = client.get("https://uselessfacts.jsph.pl/api/v2/facts/random")
        val randomFact = response.body<Fact>()
        repository.addFact(randomFact)
        call.respond(randomFact)
    }

    get<Facts.ShortId> { params ->
        call.respond(repository.getFact(params.shortId))
    }
}