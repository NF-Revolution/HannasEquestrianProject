package com.nfrevolution.hannasequestrianproject.horses.presentation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
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

public val HorsesScreen: ComposeNavDestination<Unit> by navDestination {
    HorsesScreenContent()
}

@Composable
public fun HorsesScreenContent() {
    Scaffold(
        topBar = {
            CommonTopBar(
                title = "Horses",
                drawerState = LocalDrawerState.current
            )
        },
        containerColor = Color.Transparent
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Text("Horses Feature")
        }
    }
}
