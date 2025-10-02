package com.nfrevolution.hannasequestrianproject

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@Composable
fun App() {
    MaterialTheme {
        Box(
            modifier = Modifier,
            contentAlignment = Alignment.Center
        ) {
            VideoPlayer(
                modifier = Modifier.fillMaxSize(),
                urlOrUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                controls = false,
                autoPlay = true,
                loop = true,
                muted = false,
                posterUrl = null,
                minWidthPx = 640,
                minHeightPx = 360,
            )
        }
    }
}