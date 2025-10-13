package com.nfrevolution.hannasequestrianproject.home.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composegears.tiamat.compose.ComposeNavDestination
import com.composegears.tiamat.compose.navController
import com.composegears.tiamat.compose.navDestination
import com.composegears.tiamat.navigation.NavController
import com.nfrevolution.hannasequestrianproject.foundation.CommonTopBar
import com.nfrevolution.hannasequestrianproject.foundation.NestedComposeViewport
import com.nfrevolution.hannasequestrianproject.foundation.VideoPlayer
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.NavigationDrawerScreenContent
import com.nfrevolution.hannasequestrianproject.theme.AppTheme

public val HomeScreen: ComposeNavDestination<Unit> by navDestination {
    val navController = navController()
    HomeScreenContent(navController)
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun HomeScreenContent(navController: NavController) {
    val drawerState = rememberDrawerState(initialValue = DrawerValue.Closed)

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        VideoPlayer(
            modifier = Modifier.fillMaxSize(),
            urlOrUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            controls = false,
            autoPlay = true,
            loop = true,
            muted = false,
            posterUrl = null,
            minWidthPx = 640,
            minHeightPx = 360,
        )

        NestedComposeViewport(
            modifier = Modifier.fillMaxSize()
        ) {
            AppTheme {
                NavigationDrawerScreenContent(
                    navController = navController,
                    drawerState = drawerState,
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
                        Box(modifier = Modifier.fillMaxSize())
                    }
                }
            }
        }
    }
}