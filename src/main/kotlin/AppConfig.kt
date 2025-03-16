package com.challenge

import io.ktor.server.application.*
import org.slf4j.event.Level

object AppConfig {
    lateinit var baseUrl: String
    lateinit var logLevel: Level
    lateinit var accessToken: String

    fun init(environment: ApplicationEnvironment) {
        val env = environment.config.property("ktor.environment").getString()
        val config = environment.config.config("ktor.config.$env")
        this.baseUrl = config.propertyOrNull("baseUrl")?.getString() ?: "http://0.0.0.0:8080"
        this.accessToken = config.property("accessToken").getString()
        this.logLevel = Level.valueOf(config.propertyOrNull("logLevel")?.getString() ?: "INFO")
    }
}