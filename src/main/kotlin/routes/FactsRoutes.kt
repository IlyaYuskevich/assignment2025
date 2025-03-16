package com.challenge.routes

import com.challenge.api.UselessFactsExternal
import com.challenge.model.Fact
import com.challenge.repositories.FactRepository
import com.challenge.resources.Facts
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.server.resources.*
import io.ktor.server.response.*
import io.ktor.server.routing.Route

fun Route.factsRoutes(repository: FactRepository, client: HttpClient) {
    get<Facts> { params ->
        call.respond(repository.getFacts(params.offset, params.limit))
    }

    post<Facts> {
        val randomFact = client.get(UselessFactsExternal.Random()).body<Fact>()
        repository.addFact(randomFact)
        call.respond(randomFact)
    }

    get<Facts.ShortId> { params ->
        call.respond(repository.getFact(params.shortId))
    }
}