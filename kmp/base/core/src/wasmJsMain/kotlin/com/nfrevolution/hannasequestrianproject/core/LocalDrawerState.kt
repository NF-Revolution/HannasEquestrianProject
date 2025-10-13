package com.nfrevolution.hannasequestrianproject.core

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.staticCompositionLocalOf

/**
 * CompositionLocal for providing DrawerState throughout the app navigation tree.
 * This allows all screens to access the same drawer state without passing it explicitly.
 */
public val LocalDrawerState: ProvidableCompositionLocal<DrawerState> =
    staticCompositionLocalOf {
        error("DrawerState not provided. Make sure to wrap your navigation in CompositionLocalProvider with LocalDrawerState.")
    }



