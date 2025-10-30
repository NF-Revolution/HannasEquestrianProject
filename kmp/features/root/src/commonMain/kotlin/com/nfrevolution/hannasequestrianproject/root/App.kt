package com.nfrevolution.hannasequestrianproject.root

import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import com.nfrevolution.hannasequestrianproject.about.presentation.AboutScreen
import com.nfrevolution.hannasequestrianproject.home.data.di.HomeDataModule
import com.nfrevolution.hannasequestrianproject.home.domain.di.HomeDomainModule
import com.nfrevolution.hannasequestrianproject.home.presentation.HomeScreen
import com.nfrevolution.hannasequestrianproject.home.presentation.di.HomePresentationModule
import com.nfrevolution.hannasequestrianproject.horses.presentation.HorsesScreen
import com.nfrevolution.hannasequestrianproject.maincontent.di.MainContentModule
import com.nfrevolution.hannasequestrianproject.maincontent.presentation.MainContentScreenContent
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.di.NavigationDrawerModule
import com.nfrevolution.hannasequestrianproject.network.di.NetworkModule
import com.nfrevolution.hannasequestrianproject.root.di.RootModule
import com.nfrevolution.hannasequestrianproject.stables.presentation.StablesScreen
import com.nfrevolution.hannasequestrianproject.theme.AppTheme
import org.koin.compose.KoinApplication
import org.koin.ksp.generated.module


@Composable
public fun App() {
    KoinApplication(
        application = {
            modules(
                NetworkModule().module,
                RootModule().module,
                NavigationDrawerModule().module,
                MainContentModule().module,
                HomeDomainModule().module,
                HomeDataModule().module,
                HomePresentationModule().module,
            )
        }
    ) {
        AppTheme {
            Surface {
                MainContentScreenContent(
                    startDestination = HomeScreen,
                    destinations = arrayOf(
                        HomeScreen,
                        HorsesScreen,
                        StablesScreen,
                        AboutScreen,
                    )
                )
            }
        }
    }
}
