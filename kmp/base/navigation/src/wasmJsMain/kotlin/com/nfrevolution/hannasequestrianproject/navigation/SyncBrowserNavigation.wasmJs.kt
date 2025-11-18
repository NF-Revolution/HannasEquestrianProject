package com.nfrevolution.hannasequestrianproject.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import com.composegears.tiamat.compose.popToTop
import com.composegears.tiamat.navigation.NavController
import com.composegears.tiamat.navigation.NavDestination
import kotlinx.browser.window
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import org.w3c.dom.events.Event

@Composable
private fun handleInitialRoute(
    navController: NavController,
    routeToDestination: Map<String, NavDestination<*>>
) {
    LaunchedEffect(Unit) {
        val initialRoute = window.location.pathname

        if (initialRoute != "/") {
            val targetDestination = routeToDestination[initialRoute]

            if (targetDestination != null) {
                navController.popToTop(targetDestination)
            }
        }
    }
}

@Composable
private fun handleBrowserBackForward(
    navController: NavController,
    destinationRoutes: Map<NavDestination<*>, String>,
    routeToDestination: Map<String, NavDestination<*>>
) {
    DisposableEffect(navController, destinationRoutes) {
        val handlePopState: (Event) -> Unit = { _ ->
            val targetRoute = window.location.pathname
            val targetDestination = routeToDestination[targetRoute]

            if (targetDestination != null) {
                navController.popToTop(targetDestination)
            }
        }

        window.addEventListener("popstate", handlePopState)

        onDispose {
            window.removeEventListener("popstate", handlePopState)
        }
    }
}

@OptIn(ExperimentalWasmJsInterop::class)
@Composable
private fun syncNavigationToBrowserHistory(
    navController: NavController,
    destinationRoutes: Map<NavDestination<*>, String>
) {
    LaunchedEffect(navController) {
        navController.navStateFlow
            .map { state -> state.stack.lastOrNull()?.destination }
            .distinctUntilChanged()
            .collect { currentDestination ->
                currentDestination?.let { dest ->
                    val route = destinationRoutes[dest] ?: "/"
                    val currentRoute = window.location.pathname

                    if (route != currentRoute) {
                        window.history.pushState(null, "", route)
                    }
                }
            }
    }
}

@Composable
public actual fun syncBrowserNavigationIfSupported(
    navController: NavController,
    destinationRoutes: Map<NavDestination<*>, String>
) {
    val routeToDestination = remember(destinationRoutes) {
        destinationRoutes.entries.associate { (dest, route) -> route to dest }
    }

    handleInitialRoute(navController, routeToDestination)
    handleBrowserBackForward(navController, destinationRoutes, routeToDestination)
    syncNavigationToBrowserHistory(navController, destinationRoutes)
}
