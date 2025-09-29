package com.nfrevolution.hannasequestrianproject

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

/**
 * Cross-platform video player for Web targets (JS and Wasm).
 * This uses the new Web interoperability API to mount an HTML <video> element
 * within the Compose layout (no manual body overlays).
 */
@Composable
expect fun VideoPlayer(
    urlOrUri: String,
    modifier: Modifier = Modifier,
    controls: Boolean = true,
    autoPlay: Boolean = false,
    loop: Boolean = false,
    muted: Boolean = false,
    posterUrl: String? = null,
    minWidthPx: Int = 320,
    minHeightPx: Int = 180,
    content: @Composable BoxScope.() -> Unit
)
