package com.nfrevolution.hannasequestrianproject.navigation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import com.composegears.tiamat.compose.Navigation
import com.composegears.tiamat.compose.navigationPlatformDefault
import com.composegears.tiamat.compose.rememberNavController
import com.composegears.tiamat.navigation.NavDestination

@Composable
@OptIn(ExperimentalComposeUiApi::class)
public fun HannasNavigation(
    modifier: Modifier = Modifier,
    startDestination: NavDestination<*>,
    destinations: Array<NavDestination<*>>,
) {
    val navController = rememberNavController(
        startDestination = startDestination,
    )

    Navigation(
        navController = navController,
        modifier = modifier.fillMaxSize(),
        destinations = destinations,
        contentTransformProvider = { navigationPlatformDefault(it) }
    )

}
