package com.nfrevolution.hannasequestrianproject

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform