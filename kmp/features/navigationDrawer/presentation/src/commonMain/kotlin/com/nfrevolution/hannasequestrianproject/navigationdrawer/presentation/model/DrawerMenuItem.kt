package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model

import com.composegears.tiamat.navigation.NavDestination
import org.jetbrains.compose.resources.StringResource

internal data class DrawerMenuItem(
    val title: StringResource,
    val destination: NavDestination<Unit>,
)