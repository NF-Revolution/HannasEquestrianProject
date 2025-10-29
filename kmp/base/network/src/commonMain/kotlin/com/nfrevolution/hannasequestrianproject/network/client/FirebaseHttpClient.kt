package com.nfrevolution.hannasequestrianproject.network.client

import com.nfrevolution.hannasequestrianproject.core.AppConfig
import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.defaultRequest
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.plugins.logging.SIMPLE
import io.ktor.http.URLProtocol
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json

internal fun createFirebaseDatabaseClient(): HttpClient = HttpClient {
    expectSuccess = true

    defaultRequest {
        url {
            protocol = URLProtocol.HTTPS
            host = AppConfig.databaseUrlHost
        }
    }

    install(ContentNegotiation) {
        json(Json {
            prettyPrint = true
            isLenient = true
            ignoreUnknownKeys = true
            encodeDefaults = true
        })
    }

    install(Logging) {
        logger = Logger.SIMPLE
        level = LogLevel.INFO
    }
}
