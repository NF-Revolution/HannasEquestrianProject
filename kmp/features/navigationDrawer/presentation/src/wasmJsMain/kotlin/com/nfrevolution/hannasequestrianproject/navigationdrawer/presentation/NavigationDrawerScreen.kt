package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import com.composegears.tiamat.compose.popToTop
import com.composegears.tiamat.navigation.NavController
import org.koin.compose.koinInject
import pro.respawn.flowmvi.compose.dsl.subscribe

@Composable
public fun NavigationDrawerScreenContent(
    navController: NavController,
    drawerState: DrawerState,
    content: @Composable () -> Unit
) {
    val viewModel = koinInject<NavigationDrawerViewModel>()
    val state by viewModel.store.subscribe { action ->
        when (action) {
            is NavigationDrawerAction.NavigateTo -> {
                navController.popToTop(action.destination)
            }
        }
    }

    CommonNavigationDrawer(
        drawerState = drawerState,
        currentItem = state.menuItem,
        menuItems = viewModel.drawerDestinations,
        headerContent = {
            DefaultDrawerHeader(
                appIcon = { }
            )
        },
        onMenuClick = { item ->
            viewModel.store.intent(NavigationDrawerIntent.NavigateTo(item))
        },
        content = content,
    )
}
