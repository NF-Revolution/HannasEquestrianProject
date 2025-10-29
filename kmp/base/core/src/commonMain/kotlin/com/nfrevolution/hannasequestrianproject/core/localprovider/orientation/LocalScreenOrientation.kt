package com.nfrevolution.hannasequestrianproject.core.localprovider.orientation

import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal for providing screen orientation throughout the app.
 * This allows all screens to access the current screen orientation without passing it explicitly.
 */
public val LocalScreenOrientation: ProvidableCompositionLocal<ScreenOrientation> =
    staticCompositionLocalOf {
        error("ScreenOrientation not provided. Make sure to wrap your content in CompositionLocalProvider with LocalScreenOrientation.")
    }
