package com.nfrevolution.hannasequestrianproject

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application
import com.nfrevolution.hannasequestrianproject.root.App

public fun main(): Unit = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Hanna's Equestrian Project",
    ) {
        App()
    }
}