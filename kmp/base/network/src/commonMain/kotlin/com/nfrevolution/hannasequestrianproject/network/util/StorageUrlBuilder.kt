package com.nfrevolution.hannasequestrianproject.network.util

import com.nfrevolution.hannasequestrianproject.core.AppConfig

/**
 * Builds a complete Firebase Storage URL from this storage path.
 *
 * @receiver The storage path (e.g., "videos/intro.mp4")
 * @return The full Firebase Storage URL with proper encoding and media token
 */
public fun String.buildStorageUrl(): String {
    val encodedPath = this.split("/").joinToString("%2F")
    return "${AppConfig.storageUrl}${encodedPath}?alt=media"
}
