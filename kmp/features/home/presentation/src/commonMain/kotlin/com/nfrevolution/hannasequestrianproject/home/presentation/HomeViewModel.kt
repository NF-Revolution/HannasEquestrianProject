package com.nfrevolution.hannasequestrianproject.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfrevolution.hannasequestrianproject.home.domain.usecase.GetHomeConfigUseCase
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.navigationdrawer.domain.drawerNavigator.DrawerNavigator
import org.koin.android.annotation.KoinViewModel
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.reduce

@KoinViewModel
internal class HomeViewModel(
    private val drawerNavigator: DrawerNavigator,
    private val featureProvider: FeatureProvider,
    private val getHomeConfigUseCase: GetHomeConfigUseCase,
) : ViewModel(),
    Container<HomeUiState, HomeIntent, HomeAction> {

    override val store by lazyStore(
        initial = HomeUiState.ConfigLoading,
        scope = viewModelScope
    ) {
        reduce { intent ->
            when (intent) {
                HomeIntent.LoadConfig -> {
                    updateState { HomeUiState.ConfigLoading }
                    getHomeConfigUseCase()
                        .onSuccess { config ->
                            updateState { HomeUiState.VideoLoading(videoUrl = config.introVideoUrl) }
                        }
                        .onFailure { error ->
                            updateState {
                                val msg = error.message ?: "Unknown error"
                                println("Error loading config: $msg")

                                HomeUiState.Error(
                                    message = msg
                                )
                            }
                        }
                }

                HomeIntent.OnVideoLoaded -> {
                    withState {
                        if (this is HomeUiState.VideoLoading) {
                            updateState { HomeUiState.Success(videoUrl = videoUrl) }
                        }
                    }
                }

                HomeIntent.OnBoxClicked -> {
                    drawerNavigator.navigateTo(featureProvider.horses)
                }

                HomeIntent.OnScreenDisposed -> {
                    withState {
                        if (this is HomeUiState.Success) {
                            updateState { HomeUiState.VideoLoading(videoUrl = videoUrl) }
                        } else {
                            updateState { HomeUiState.ConfigLoading }
                        }
                    }
                }
            }
        }
    }
}

internal sealed interface HomeUiState : MVIState {
    data object ConfigLoading : HomeUiState
    data class VideoLoading(val videoUrl: String) : HomeUiState
    data class Success(val videoUrl: String) : HomeUiState
    data class Error(val message: String) : HomeUiState
}

internal sealed interface HomeIntent : MVIIntent {
    data object LoadConfig : HomeIntent
    data object OnVideoLoaded : HomeIntent
    data object OnBoxClicked : HomeIntent
    data object OnScreenDisposed : HomeIntent
}

internal sealed interface HomeAction : MVIAction
