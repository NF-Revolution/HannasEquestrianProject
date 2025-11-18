package com.nfrevolution.hannasequestrianproject.navigation

import androidx.compose.runtime.Composable
import com.composegears.tiamat.navigation.NavController
import com.composegears.tiamat.navigation.NavDestination

/**
 * Platform-specific browser navigation synchronization.
 * On web platforms, this syncs navigation with browser URL and history.
 * On other platforms, this is a no-op.
 */
@Composable
public expect fun syncBrowserNavigationIfSupported(
    navController: NavController,
    destinationRoutes: Map<NavDestination<*>, String>
)
