package com.nfrevolution.hannasequestrianproject.home.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composegears.tiamat.compose.ComposeNavDestination
import com.composegears.tiamat.compose.navController
import com.composegears.tiamat.compose.navDestination
import com.composegears.tiamat.navigation.NavController
import com.nfrevolution.hannasequestrianproject.core.localprovider.orientation.LocalScreenOrientation
import com.nfrevolution.hannasequestrianproject.core.localprovider.orientation.ScreenOrientation
import com.nfrevolution.hannasequestrianproject.foundation.CommonTopBar
import com.nfrevolution.hannasequestrianproject.foundation.NestedComposeViewport
import com.nfrevolution.hannasequestrianproject.foundation.VideoPlayer
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerScreenContent
import com.nfrevolution.hannasequestrianproject.resources.icons.HannasEquestrianProject
import com.nfrevolution.hannasequestrianproject.resources.icons.filled.JumpingHorseLogo
import com.nfrevolution.hannasequestrianproject.theme.AppTheme
import org.koin.compose.viewmodel.koinViewModel
import pro.respawn.flowmvi.compose.dsl.subscribe

public val HomeScreen: ComposeNavDestination<Unit> by navDestination {
    val navController = navController()
    HomeScreenContent(navController)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun HomeScreenContent(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)
    val orientation = LocalScreenOrientation.current
    val viewModel = koinViewModel<HomeViewModel>()
    val state by viewModel.store.subscribe { action ->
        handleAction(action)
    }
    var videoUrl by remember { mutableStateOf("") }
    var isIconVisible by remember { mutableStateOf(true) }

    println("videoUrl = $videoUrl, state $state")

    LaunchedEffect(Unit) {
        viewModel.store.intent(HomeIntent.LoadConfig)
    }

    updateStateVariables(
        state = state,
        onVideoUrlUpdate = { url -> videoUrl = url },
        onIconVisibilityUpdate = { visible -> isIconVisible = visible }
    )

    Box(modifier = Modifier.fillMaxSize()) {
        BackgroundVideo(
            videoUrl = videoUrl,
            onVideoLoaded = { viewModel.store.intent(HomeIntent.OnVideoLoaded) }
        )
        HomeScreenOverlay(
            navController = navController,
            drawerState = drawerState,
            isIconVisible = isIconVisible,
            orientation = orientation,
            onBoxClicked = { viewModel.store.intent(HomeIntent.OnBoxClicked) }
        )
    }
}

@Composable
private fun BackgroundVideo(
    videoUrl: String,
    onVideoLoaded: () -> Unit
) {
    if (videoUrl.isNotEmpty()) {
        VideoPlayer(
            modifier = Modifier.fillMaxSize(),
            urlOrUri = videoUrl,
            controls = false,
            autoPlay = true,
            loop = true,
            muted = true,
            posterUrl = null,
            minWidthPx = 640,
            minHeightPx = 360,
            onLoaded = onVideoLoaded
        )
    }
}

@Composable
private fun HomeScreenOverlay(
    navController: NavController,
    drawerState: DrawerState,
    isIconVisible: Boolean,
    orientation: ScreenOrientation,
    onBoxClicked: () -> Unit
) {
    NestedComposeViewport(modifier = Modifier.fillMaxSize()) {
        AppTheme {
            NavigationDrawerScreenContent(
                navController = navController,
                drawerState = drawerState,
            ) {
                HomeScaffold(
                    drawerState = drawerState,
                    isIconVisible = isIconVisible,
                    orientation = orientation,
                    onBoxClicked = onBoxClicked
                )
            }
        }
    }
}

@Composable
private fun HomeScaffold(
    drawerState: DrawerState,
    isIconVisible: Boolean,
    orientation: ScreenOrientation,
    onBoxClicked: () -> Unit
) {
    Scaffold(
        topBar = {
            CommonTopBar(
                title = "Home",
                drawerState = drawerState
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        LoadingIconOverlay(
            isVisible = isIconVisible,
            orientation = orientation
        )
        InteractionLayer(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onClick = onBoxClicked
        )
    }
}

@Composable
private fun LoadingIconOverlay(
    orientation: ScreenOrientation,
    isVisible: Boolean
) {
    val iconFraction = remember(orientation) {
        when (orientation) {
            ScreenOrientation.PORTRAIT -> 0.64f
            ScreenOrientation.LANDSCAPE -> 0.36f
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.onSurface),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(iconFraction),
            imageVector = HannasEquestrianProject.Filled.JumpingHorseLogo,
            contentDescription = "Jumping Horse Logo",
            tint = MaterialTheme.colorScheme.surface,
        )
    }
}

@Composable
private fun InteractionLayer(
    modifier: Modifier = Modifier,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onClick
            )
    )
}

@Composable
private fun updateStateVariables(
    state: HomeUiState,
    onVideoUrlUpdate: (String) -> Unit,
    onIconVisibilityUpdate: (Boolean) -> Unit
) {
    when (state) {
        is HomeUiState.VideoLoading -> {
            onVideoUrlUpdate(state.videoUrl)
        }

        is HomeUiState.ConfigLoading -> Unit
        is HomeUiState.Success -> {
            onIconVisibilityUpdate(false)
        }

        is HomeUiState.Error -> Unit
    }
}

private fun handleAction(action: HomeAction) {
    when (action) {
        is HomeAction.NavigateToHorses -> {
            println("NavigateToHorses")
        }
    }
}