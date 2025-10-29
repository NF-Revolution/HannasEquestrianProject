package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public actual fun VideoPlayer(
    urlOrUri: String,
    modifier: Modifier,
    controls: Boolean,
    autoPlay: Boolean,
    loop: Boolean,
    muted: Boolean,
    posterUrl: String?,
    minWidthPx: Int,
    minHeightPx: Int,
    onLoaded: () -> Unit,
) {
    Box(modifier = modifier)
}
