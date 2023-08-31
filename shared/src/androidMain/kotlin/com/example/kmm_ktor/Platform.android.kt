package com.example.kmm_ktor

import io.github.aakira.napier.DebugAntilog
import io.github.aakira.napier.Napier
import io.ktor.client.HttpClient
import io.ktor.client.HttpClientConfig
import io.ktor.client.engine.okhttp.OkHttp
import java.util.concurrent.TimeUnit

actual class Platform actual constructor() {
    actual val platform: String = "Android ${android.os.Build.VERSION.SDK_INT}"
}

actual fun httpClient(config: HttpClientConfig<*>.() -> Unit) = HttpClient(OkHttp) {
    config(this)
    engine {
        config {
            // Retry or not when a connectivity problem is encountered.
            retryOnConnectionFailure(true)

            // Set connection timeout of TCP socket to target-host for new connection (default is 10s)
            connectTimeout(5, TimeUnit.SECONDS)
        }
    }
}

actual fun initLogger() {
    Napier.base(DebugAntilog())
}
