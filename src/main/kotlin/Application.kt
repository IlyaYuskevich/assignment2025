package com.challenge

import com.challenge.plugins.*
import com.challenge.repositories.FactRepository
import com.challenge.repositories.InMemoryFactRepository
import io.ktor.client.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module(client: HttpClient = createClient(), repository: FactRepository = InMemoryFactRepository()) {
    AppConfig.init(environment)

    configureHTTP()
    configureSecurity(AppConfig.accessToken)
    configureSerialization()
    configureResources()
    configureLogging(AppConfig.logLevel)
    configureRoutes(client, repository)
}


