package com.nfrevolution.hannasequestrianproject.about.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.composegears.tiamat.compose.ComposeNavDestination
import com.composegears.tiamat.compose.navDestination
import com.nfrevolution.hannasequestrianproject.core.LocalDrawerState
import com.nfrevolution.hannasequestrianproject.foundation.CommonTopBar

public val AboutScreen: ComposeNavDestination<Unit> by navDestination {
    AboutScreenContent()
}

@Composable
public fun AboutScreenContent() {
    Scaffold(
        topBar = {
            CommonTopBar(
                title = "About",
                drawerState = LocalDrawerState.current
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = "Welcome to About screen!",
                style = MaterialTheme.typography.headlineLarge
            )
        }
    }
}
