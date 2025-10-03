package com.nfrevolution.hannasequestrianproject.core

internal expect val currentPlatform: PlatformName

public data object Platform {
    public val name: PlatformName
        get() = currentPlatform
}
