package com.nfrevolution.hannasequestrianproject.maincontent.presentation

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
import com.nfrevolution.hannasequestrianproject.core.LocalDrawerState
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerScreenContent

@Composable
public fun MainContentScreenContent(
    startDestination: NavDestination<*>,
    destinations: Array<NavDestination<*>>,
) {
    val navController = rememberNavController(
        startDestination = startDestination,
    )
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    CompositionLocalProvider(LocalDrawerState provides drawerState) {
        NavigationDrawerScreenContent(
            navController,
            drawerState,
        ) {
            Navigation(
                navController = navController,
                modifier = Modifier
                    .fillMaxSize(),
                destinations = destinations,
                contentTransformProvider = { navigationPlatformDefault(it) }
            )
        }
    }
}
