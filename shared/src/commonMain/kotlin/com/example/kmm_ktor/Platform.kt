package com.example.kmm_ktor

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform