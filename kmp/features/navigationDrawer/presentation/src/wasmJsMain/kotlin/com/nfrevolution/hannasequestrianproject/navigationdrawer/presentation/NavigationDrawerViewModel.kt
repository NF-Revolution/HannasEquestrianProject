package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerAction.NavigateTo
import org.koin.core.annotation.Single
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.reduce

@Single
internal class NavigationDrawerViewModel(
    featureProvider: FeatureProvider,
) : ViewModel(),
    Container<NavigationDrawerUiState, NavigationDrawerIntent, NavigationDrawerAction> {

    val drawerDestinations = listOf(
        DrawerMenuItem(
            "Horses",
            featureProvider.horses,
        ),
        DrawerMenuItem(
            "Stables",
            featureProvider.stables,
        ),
        DrawerMenuItem(
            "About",
            featureProvider.about,
        ),
    )

    override val store by lazyStore(
        initial = NavigationDrawerUiState(null),
        scope = viewModelScope
    ) {
        reduce { intent ->
            when (intent) {
                is NavigationDrawerIntent.NavigateTo -> {
                    updateState { copy(intent.menuItem) }
                    action(NavigateTo(intent.menuItem.destination))
                }
            }
        }
    }
}


internal data class NavigationDrawerUiState(val menuItem: DrawerMenuItem?) : MVIState

internal sealed interface NavigationDrawerIntent : MVIIntent {
    data class NavigateTo(val menuItem: DrawerMenuItem) : NavigationDrawerIntent
}

internal sealed interface NavigationDrawerAction : MVIAction {
    data class NavigateTo(
        val destination: NavDestination<Unit>
    ) : NavigationDrawerAction

}