package com.nfrevolution.hannasequestrianproject.navigation.featureProvider

import com.composegears.tiamat.navigation.NavDestination

public interface FeatureProvider {
    public val home: NavDestination<Unit>
    public val horses: NavDestination<Unit>
    public val stables: NavDestination<Unit>
    public val about: NavDestination<Unit>
}
