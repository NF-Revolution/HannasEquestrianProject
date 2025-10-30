package com.nfrevolution.hannasequestrianproject.navigationdrawer.domain.drawerNavigator

import com.composegears.tiamat.navigation.NavDestination

public interface DrawerNavigator {
    public fun navigateTo(destination: NavDestination<Unit>)
}
