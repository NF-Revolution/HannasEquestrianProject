package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.WebElementView
import kotlinx.browser.document
import org.w3c.dom.HTMLVideoElement

@OptIn(ExperimentalComposeUiApi::class, ExperimentalWasmJsInterop::class)
@Composable
public fun VideoPlayer(
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
                applyBaseAttributes()
                applyVideoStyles(minWidthPx, minHeightPx)
                configureVideoElement(
                    urlOrUri = urlOrUri,
                    controls = controls,
                    loop = loop,
                    muted = muted,
                    posterUrl = posterUrl,
                    autoPlay = autoPlay
                )
            }
        },
        modifier = modifier,
        update = { video ->
            video.applyVideoStyles(minWidthPx, minHeightPx)
            video.configureVideoElement(
                urlOrUri = urlOrUri,
                controls = controls,
                loop = loop,
                muted = muted,
                posterUrl = posterUrl,
                autoPlay = autoPlay
            )
        }
    )
}

private fun HTMLVideoElement.applyBaseAttributes() {
    setAttribute("playsinline", "")
    setAttribute("preload", "metadata")
    setAttribute("crossorigin", "anonymous")
    style.display = "block"
}

private fun HTMLVideoElement.applyVideoStyles(minWidthPx: Int, minHeightPx: Int) {
    style.width = "100%"
    style.height = "100%"
    style.objectFit = "cover"
    style.margin = "0"
    style.padding = "0"
    style.boxSizing = "border-box"
    style.minWidth = "${minWidthPx}px"
    style.minHeight = "${minHeightPx}px"
    style.backgroundColor = "#000"
}

@OptIn(ExperimentalWasmJsInterop::class)
private fun HTMLVideoElement.configureVideoElement(
    urlOrUri: String,
    controls: Boolean,
    loop: Boolean,
    muted: Boolean,
    posterUrl: String?,
    autoPlay: Boolean
) {
    this.controls = controls
    this.loop = loop
    this.muted = muted
    if (posterUrl != null) {
        poster = posterUrl
    } else {
        removeAttribute("poster")
    }
    if (src != urlOrUri) {
        src = urlOrUri
    }
    if (autoPlay) {
        if (!this.muted) {
            this.muted = true
        }
        runCatching { play() }
    }
}
