package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model

import androidx.compose.ui.graphics.vector.ImageVector
import org.jetbrains.compose.resources.StringResource

internal data class SocialMediaItem(
    val name: StringResource,
    val link: String,
    val icon: ImageVector
)
