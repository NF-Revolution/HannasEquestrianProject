package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public expect fun VideoPlayer(
    urlOrUri: String,
    modifier: Modifier,
    controls: Boolean,
    autoPlay: Boolean,
    loop: Boolean,
    muted: Boolean,
    posterUrl: String?,
    minWidthPx: Int,
    minHeightPx: Int,
    onLoaded: () -> Unit = {},
)
