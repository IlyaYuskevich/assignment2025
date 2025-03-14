package com.challenge

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.plugins.swagger.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        post("/facts") {
            call.respond(HttpStatusCode.OK)
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
