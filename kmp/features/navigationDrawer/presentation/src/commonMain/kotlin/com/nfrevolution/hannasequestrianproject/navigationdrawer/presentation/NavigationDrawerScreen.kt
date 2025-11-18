package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import androidx.compose.material3.DrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import com.composegears.tiamat.compose.popToTop
import com.composegears.tiamat.navigation.NavController
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.ui.CommonNavigationDrawer
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
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

    LaunchedEffect(navController) {
        navController.navStateFlow
            .map { it.stack.lastOrNull()?.destination }
            .distinctUntilChanged()
            .collect { currentDestination ->
                currentDestination?.let { dest ->
                    viewModel.store.intent(NavigationDrawerIntent.SyncSelectedItem(dest))
                }
            }
    }

    CommonNavigationDrawer(
        drawerState = drawerState,
        currentItem = state.selectedItem,
        menuItems = viewModel.drawerSetup.menuItems,
        socialItems = viewModel.drawerSetup.socialItems,
        copyright = viewModel.drawerSetup.copyright,
        onMenuClick = { item ->
            viewModel.store.intent(NavigationDrawerIntent.NavigateTo(item))
        },
        onLogoClick = {
            viewModel.store.intent(NavigationDrawerIntent.NavigateToHome)
        },
        content = content,
    )
}
