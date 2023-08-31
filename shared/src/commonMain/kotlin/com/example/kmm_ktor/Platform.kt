package com.example.kmm_ktor

import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig

expect class Platform() {
    val platform: String
}

expect fun httpClient(config:HttpClientConfig<*>.() -> Unit):HttpClient

expect fun initLogger()
