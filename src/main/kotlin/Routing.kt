package com.challenge

import com.challenge.model.Fact
import com.challenge.model.Facts
import io.ktor.client.call.*
import io.ktor.client.plugins.resources.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {

    routing {

        post("/facts") {
            val randomFact = client.get(Facts.RandomFact()).body<Fact>()
            call.respond(randomFact)
        }

        get("/facts/{shortenedUrl}") {
            call.respond(HttpStatusCode.OK)
        }

        get("/facts") {
            call.respondText("Hey hey")
        }

        get("/admin/statistics") {
            call.respond(HttpStatusCode.OK)
        }
    }
}
