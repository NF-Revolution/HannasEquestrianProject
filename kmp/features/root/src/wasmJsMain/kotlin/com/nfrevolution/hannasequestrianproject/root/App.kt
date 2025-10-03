package com.nfrevolution.hannasequestrianproject.root

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nfrevolution.hannasequestrianproject.home.presentation.HomeScreen
import com.nfrevolution.hannasequestrianproject.navigation.HannasNavigation
import com.nfrevolution.hannasequestrianproject.root.di.RootModule
import com.nfrevolution.hannasequestrianproject.theme.HannasTheme
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module

@Composable
public fun App() {
    KoinApplication(
        application = {
            modules(
                RootModule().module,
            )
        }
    ) {
        HannasTheme {
            Surface {
                HannasNavigation(
                    modifier = Modifier.fillMaxSize(),
                    startDestination = HomeScreen,
                    destinations = arrayOf(
                        HomeScreen
                    ),
                )
            }
        }
    }
}
