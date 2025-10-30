package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.drawerNavigator

import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.domain.drawerNavigator.DrawerNavigator
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerIntent
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerViewModel
import org.koin.core.annotation.Factory
import pro.respawn.flowmvi.dsl.intent

@Factory
internal class DrawerNavigatorImpl(
    private val navigationDrawerViewModel: NavigationDrawerViewModel,
    private val featureProvider: FeatureProvider
) : DrawerNavigator {
    override fun navigateTo(destination: NavDestination<Unit>) {
        when (destination) {
            featureProvider.home -> {
                navigationDrawerViewModel.intent(NavigationDrawerIntent.NavigateToHome)
            }

            else -> {
                navigationDrawerViewModel.drawerSetup.menuItems
                    .firstOrNull { it.destination == destination }
                    ?.let {
                        navigationDrawerViewModel.intent(NavigationDrawerIntent.NavigateTo(it))
                    }
            }
        }
    }
}