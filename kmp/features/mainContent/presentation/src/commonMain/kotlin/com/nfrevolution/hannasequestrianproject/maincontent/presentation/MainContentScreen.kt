package com.nfrevolution.hannasequestrianproject.maincontent.presentation

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.ui.Modifier
import com.composegears.tiamat.compose.Navigation
import com.composegears.tiamat.compose.navigationPlatformDefault
import com.composegears.tiamat.compose.rememberNavController
import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.core.localprovider.drawer.LocalDrawerState
import com.nfrevolution.hannasequestrianproject.core.localprovider.orientation.LocalScreenOrientation
import com.nfrevolution.hannasequestrianproject.core.localprovider.orientation.ScreenOrientation
import com.nfrevolution.hannasequestrianproject.navigation.syncBrowserNavigationIfSupported
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerScreenContent

@Composable
public fun MainContentScreenContent(
    startDestination: NavDestination<*>,
    destinationRoutes: Map<NavDestination<*>, String> = emptyMap()
) {
    val destinations = destinationRoutes.keys.toTypedArray()

    val navController = rememberNavController(
        startDestination = startDestination,
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    syncBrowserNavigationIfSupported(navController, destinationRoutes)

    BoxWithConstraints(modifier = Modifier.fillMaxSize()) {
        val screenOrientation = if (maxWidth > maxHeight) {
            ScreenOrientation.LANDSCAPE
        } else {
            ScreenOrientation.PORTRAIT
        }

        CompositionLocalProvider(
            LocalDrawerState provides drawerState,
            LocalScreenOrientation provides screenOrientation
        ) {
            NavigationDrawerScreenContent(
                navController,
                drawerState,
            ) {
                Navigation(
                    navController = navController,
                    modifier = Modifier.fillMaxSize(),
                    destinations = destinations,
                    contentTransformProvider = { navigationPlatformDefault(it) }
                )
            }
        }
    }
}
