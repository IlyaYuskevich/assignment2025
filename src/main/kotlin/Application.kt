package com.challenge

import io.ktor.server.application.*

fun main(args: Array<String>) {
    io.ktor.server.netty.EngineMain.main(args)
}

fun Application.module() {
    AppConfig.init(environment)

    configureHTTP()
    configureRouting()
    configureSerialization()
    configureLogging(AppConfig.logLevel)
}


