package com.example.kmm_ktor

import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.plugins.logging.LogLevel
import io.ktor.client.plugins.logging.Logger
import io.ktor.client.plugins.logging.Logging
import io.ktor.client.request.get
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
data class Hello(val string: String)

class Greeting {
    private val httpClient = HttpClient {
        install(Logging) {
            level = LogLevel.HEADERS
            logger = object : Logger {
                override fun log(message: String) {
                    Napier.v(tag = "HTTP Client", message = message)
                }
            }
        }
        // install the specified plugin and optionally configures it
        install(ContentNegotiation) {
            // register the json type to the ContentNegotiation plugin
            json(Json {
                // Specifies whether encounters of unknown properties in the input JSON should be ignored instead of throwing SerializationException
                ignoreUnknownKeys = true
            })
        }
    }.also { initLogger() }

    @Throws(Throwable::class)
    suspend fun greeting(): String {
        return "${getHello().random().string}, ${Platform().platform}!"
    }

    private suspend fun getHello(): List<Hello> {
        return httpClient
            .get("https://cdn.jsdelivr.net/gh/KaterinaPetrova/greeting@main/greetings.json")
            .body() // Receive the body part of the response
    }
}
