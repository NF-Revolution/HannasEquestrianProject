package com.nfrevolution.hannasequestrianproject.root.navigation

import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.about.presentation.AboutScreen
import com.nfrevolution.hannasequestrianproject.home.presentation.HomeScreen
import com.nfrevolution.hannasequestrianproject.horses.presentation.HorsesScreen
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import com.nfrevolution.hannasequestrianproject.stables.presentation.StablesScreen
import org.koin.core.annotation.Single

@Single
public class CommonFeatureProvider : FeatureProvider {
    override val home: NavDestination<Unit>
        get() = HomeScreen

    override val horses: NavDestination<Unit>
        get() = HorsesScreen

    override val stables: NavDestination<Unit>
        get() = StablesScreen

    override val about: NavDestination<Unit>
        get() = AboutScreen
}
