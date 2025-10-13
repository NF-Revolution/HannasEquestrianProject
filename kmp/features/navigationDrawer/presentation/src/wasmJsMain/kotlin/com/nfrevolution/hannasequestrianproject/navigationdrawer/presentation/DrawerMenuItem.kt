package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import com.composegears.tiamat.navigation.NavDestination

/**
 * Data class representing a navigation drawer menu item
 *
 * @param title The title text to display
 * @param destination The navigation destination associated with this item
 */
internal data class DrawerMenuItem(
    val title: String,
    val destination: NavDestination<Unit>,
)