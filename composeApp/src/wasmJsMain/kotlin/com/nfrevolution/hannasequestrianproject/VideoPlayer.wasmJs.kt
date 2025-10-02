package com.nfrevolution.hannasequestrianproject

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.WebElementView
import kotlinx.browser.document
import org.w3c.dom.HTMLVideoElement

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
@Composable
actual fun VideoPlayer(
    urlOrUri: String,
    modifier: Modifier,
    controls: Boolean,
    autoPlay: Boolean,
    loop: Boolean,
    muted: Boolean,
    posterUrl: String?,
    minWidthPx: Int,
    minHeightPx: Int,
) {
    WebElementView(
        factory = {
            (document.createElement("video") as HTMLVideoElement).apply {
                setAttribute("playsinline", "")
                setAttribute("preload", "metadata")
                setAttribute("crossorigin", "anonymous")
                style.display = "block"
                // Fill container both directions; preserve aspect ratio without letterboxing
                style.width = "100%"
                style.height = "100%"
                style.objectFit = "cover"
                // No paddings/margins
                style.margin = "0"
                style.padding = "0"
                style.boxSizing = "border-box"
                // Minimum constraints
                style.minWidth = "${minWidthPx}px"
                style.minHeight = "${minHeightPx}px"
                style.backgroundColor = "#000"
                this@apply.controls = controls
                this@apply.loop = loop
                this@apply.muted = muted
                if (posterUrl != null) poster = posterUrl else removeAttribute("poster")
                src = urlOrUri
                if (autoPlay) {
                    if (!muted) this@apply.muted = true
                    try {
                        play()
                    } catch (_: Throwable) {
                    }
                }
            }
        },
        modifier = modifier,
        update = { video ->
            video.controls = controls
            video.loop = loop
            video.muted = muted
            // Maintain sizing rules on updates
            video.style.width = "100%"
            video.style.height = "100%"
            video.style.objectFit = "cover"
            video.style.margin = "0"
            video.style.padding = "0"
            video.style.boxSizing = "border-box"
            video.style.minWidth = "${minWidthPx}px"
            video.style.minHeight = "${minHeightPx}px"
            if (posterUrl != null) video.poster = posterUrl else video.removeAttribute("poster")
            if (video.src != urlOrUri) video.src = urlOrUri
            if (autoPlay) {
                if (!video.muted) video.muted = true
                try {
                    video.play()
                } catch (_: Throwable) {
                }
            }
        }
    )
}
