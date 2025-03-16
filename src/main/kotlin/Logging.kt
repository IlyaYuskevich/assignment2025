package com.challenge

import io.ktor.server.application.*
import io.ktor.server.plugins.calllogging.*
import org.slf4j.event.Level

fun Application.configureLogging(logLevel: Level) {
    install(CallLogging) {
        level = logLevel
    }
}
