package com.challenge

import com.challenge.plugins.*
import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    AppConfig.init(environment)

    configureHTTP()
    configureSecurity(AppConfig.accessToken)
    configureSerialization()
    configureResources()
    configureLogging(AppConfig.logLevel)
    configureRoutes()
}


