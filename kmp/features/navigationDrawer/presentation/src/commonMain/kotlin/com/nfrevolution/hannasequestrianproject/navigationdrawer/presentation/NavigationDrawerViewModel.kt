package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.core.AppConfig
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerAction.NavigateTo
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.Copyright
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerMenuItem
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerSetup
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.SocialMediaItem
import com.nfrevolution.hannasequestrianproject.resources.Res
import com.nfrevolution.hannasequestrianproject.resources.copyright
import com.nfrevolution.hannasequestrianproject.resources.icons.HannasEquestrianProject
import com.nfrevolution.hannasequestrianproject.resources.icons.outlined.Instagram
import com.nfrevolution.hannasequestrianproject.resources.icons.outlined.TikTok
import com.nfrevolution.hannasequestrianproject.resources.icons.outlined.YouTube
import com.nfrevolution.hannasequestrianproject.resources.menu_about
import com.nfrevolution.hannasequestrianproject.resources.menu_horses
import com.nfrevolution.hannasequestrianproject.resources.menu_stables
import com.nfrevolution.hannasequestrianproject.resources.social_instagram
import com.nfrevolution.hannasequestrianproject.resources.social_tiktok
import com.nfrevolution.hannasequestrianproject.resources.social_youtube
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.koin.core.annotation.Single
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.reduce
import kotlin.time.Clock
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

@Single
internal class NavigationDrawerViewModel(
    private val featureProvider: FeatureProvider,
) : ViewModel(),
    Container<NavigationDrawerUiState, NavigationDrawerIntent, NavigationDrawerAction> {

    val drawerSetup = DrawerSetup(
        menuItems = drawerDestinations,
        socialItems = socialItems,
        copyright = copyright,
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

                NavigationDrawerIntent.NavigateToHome -> {
                    updateState { copy(null) }
                    action(NavigateTo(featureProvider.home))
                }
            }
        }
    }

    private val drawerDestinations: List<DrawerMenuItem>
        get() = listOf(
            DrawerMenuItem(
                Res.string.menu_horses,
                featureProvider.horses,
            ),
            DrawerMenuItem(
                Res.string.menu_stables,
                featureProvider.stables,
            ),
            DrawerMenuItem(
                Res.string.menu_about,
                featureProvider.about,
            ),
        )

    private val socialItems: List<SocialMediaItem>
        get() = buildList {
            AppConfig.instagramUrl.takeIf { it.isNotBlank() }?.let {
                add(
                    SocialMediaItem(
                        name = Res.string.social_instagram,
                        link = it,
                        icon = HannasEquestrianProject.Outlined.Instagram
                    )
                )
            }
            AppConfig.youtubeUrl.takeIf { it.isNotBlank() }?.let {
                add(
                    SocialMediaItem(
                        name = Res.string.social_youtube,
                        link = it,
                        icon = HannasEquestrianProject.Outlined.YouTube
                    )
                )
            }
            AppConfig.tiktokUrl.takeIf { it.isNotBlank() }?.let {
                add(
                    SocialMediaItem(
                        name = Res.string.social_tiktok,
                        link = it,
                        icon = HannasEquestrianProject.Outlined.TikTok
                    )
                )
            }
        }

    @OptIn(ExperimentalTime::class)
    private val copyright: Copyright
        get() {
            val now: Instant = Clock.System.now()
            val currentYear = now.toLocalDateTime(TimeZone.currentSystemDefault()).year
            return Copyright(
                titleRes = Res.string.copyright,
                year = currentYear,
            )
        }
}


internal data class NavigationDrawerUiState(val selectedItem: DrawerMenuItem?) : MVIState

internal sealed interface NavigationDrawerIntent : MVIIntent {
    data class NavigateTo(val menuItem: DrawerMenuItem) : NavigationDrawerIntent

    data object NavigateToHome : NavigationDrawerIntent
}

internal sealed interface NavigationDrawerAction : MVIAction {
    data class NavigateTo(
        val destination: NavDestination<Unit>
    ) : NavigationDrawerAction

}