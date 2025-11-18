package com.nfrevolution.hannasequestrianproject.navigation

import androidx.compose.runtime.Composable
import com.composegears.tiamat.navigation.NavController
import com.composegears.tiamat.navigation.NavDestination

@Composable
public actual fun syncBrowserNavigationIfSupported(
    navController: NavController,
    destinationRoutes: Map<NavDestination<*>, String>
) {
    // No-op for JVM platform
}
