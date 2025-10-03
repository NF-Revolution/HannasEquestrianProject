package com.nfrevolution.hannasequestrianproject

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import com.nfrevolution.hannasequestrianproject.root.App

@OptIn(ExperimentalComposeUiApi::class)
public fun main() {
    ComposeViewport {
        App()
    }
}