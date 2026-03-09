package dev.liau.kmppractice

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

