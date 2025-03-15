package com.challenge

import com.challenge.model.Fact
import com.challenge.model.FactsApi
import com.challenge.storage.FactStorage
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {
        route("/facts") {
            post {
                val randomFact = client.get(FactsApi.RandomFact()).body<Fact>()
                FactStorage.addFact(randomFact)
                call.respond(randomFact)
            }

            get("{shortId}") {
                call.parameters["shortId"]?.let {
                    call.respond(FactStorage.getFact(it))
                }
            }

            get {
                val offset = call.queryParameters["offset"]?.toInt() ?: 0
                val limit = call.queryParameters["limit"]?.toInt() ?: 10
                call.respond(FactStorage.getFacts(offset, limit))
            }
        }

        route("/admin") {
            get("/statistics") {
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
