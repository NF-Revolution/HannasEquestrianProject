package com.nfrevolution.hannasequestrianproject.home.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nfrevolution.hannasequestrianproject.home.domain.usecase.GetHomeConfigUseCase
import org.koin.android.annotation.KoinViewModel
import pro.respawn.flowmvi.api.Container
import pro.respawn.flowmvi.api.MVIAction
import pro.respawn.flowmvi.api.MVIIntent
import pro.respawn.flowmvi.api.MVIState
import pro.respawn.flowmvi.dsl.lazyStore
import pro.respawn.flowmvi.plugins.reduce

@KoinViewModel
internal class HomeViewModel(
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
                                HomeUiState.Error(
                                    message = error.message ?: "Unknown error"
                                )
                            }
                        }
                }

                HomeIntent.OnVideoLoaded -> {
                    withState {
                        if (this is HomeUiState.VideoLoading) {
                            updateState { HomeUiState.Success }
                        }
                    }
                }

                HomeIntent.OnBoxClicked -> {
                    action(HomeAction.NavigateToHorses)
                }
            }
        }
    }
}

internal sealed interface HomeUiState : MVIState {
    data object ConfigLoading : HomeUiState
    data class VideoLoading(val videoUrl: String) : HomeUiState
    data object Success : HomeUiState
    data class Error(val message: String) : HomeUiState
}

internal sealed interface HomeIntent : MVIIntent {
    data object LoadConfig : HomeIntent
    data object OnVideoLoaded : HomeIntent
    data object OnBoxClicked : HomeIntent
}

internal sealed interface HomeAction : MVIAction {
    data object NavigateToHorses : HomeAction
}
