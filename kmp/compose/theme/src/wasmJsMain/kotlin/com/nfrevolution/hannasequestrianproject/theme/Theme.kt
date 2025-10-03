package com.nfrevolution.hannasequestrianproject.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val LightColorScheme = lightColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = OnPrimary,
    tertiary = Primary,
    onTertiary = OnPrimary,
    error = Error,
    onError = OnError,
    errorContainer = Error,
    onErrorContainer = OnError,
    background = Background,
    onBackground = OnBackground,
    surface = Surface,
    onSurface = OnSurface,
    surfaceVariant = SurfaceVariant,
    onSurfaceVariant = OnSurfaceVariant,
    outline = Outline,
    inverseOnSurface = OnBackgroundDark,
    inverseSurface = BackgroundDark,
    inversePrimary = Primary,
    surfaceTint = Primary,
    outlineVariant = Outline,
    scrim = OnBackground
)

private val DarkColorScheme = darkColorScheme(
    primary = Primary,
    onPrimary = OnPrimary,
    primaryContainer = PrimaryVariant,
    onPrimaryContainer = OnPrimary,
    secondary = Secondary,
    onSecondary = OnSecondary,
    secondaryContainer = SecondaryVariant,
    onSecondaryContainer = OnPrimary,
    tertiary = Primary,
    onTertiary = OnPrimary,
    error = Error,
    onError = OnError,
    errorContainer = Error,
    onErrorContainer = OnError,
    background = BackgroundDark,
    onBackground = OnBackgroundDark,
    surface = SurfaceDark,
    onSurface = OnSurfaceDark,
    surfaceVariant = SurfaceVariantDark,
    onSurfaceVariant = OnSurfaceVariantDark,
    outline = OutlineDark,
    inverseOnSurface = OnBackground,
    inverseSurface = Background,
    inversePrimary = Primary,
    surfaceTint = Primary,
    outlineVariant = OutlineDark,
    scrim = OnBackgroundDark
)

@Composable
public fun HannasTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (isDarkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = HannasTypography,
        shapes = HannasShapes,
        content = content
    )
}
