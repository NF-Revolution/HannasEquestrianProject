package com.nfrevolution.hannasequestrianproject.root.navigation

import com.composegears.tiamat.navigation.NavDestination
import com.nfrevolution.hannasequestrianproject.home.presentation.HomeScreen
import com.nfrevolution.hannasequestrianproject.navigation.featureProvider.FeatureProvider
import org.koin.core.annotation.Single

@Single
public class HEPFeatureProvider : FeatureProvider {
    override val home: NavDestination<Unit>
        get() = HomeScreen
}
