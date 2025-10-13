package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Composable function that creates a navigation drawer with customizable header and menu items
 *
 * @param modifier Modifier to be applied to the drawer sheet
 * @param currentItem The currently selected destination
 * @param menuItems List of menu items to display in the drawer
 * @param headerContent Optional composable for custom header content
 * @param onMenuClick Callback when a menu item is clicked, returns the destination
 */
@Composable
private fun AppDrawerContent(
    modifier: Modifier = Modifier,
    currentItem: DrawerMenuItem?,
    menuItems: List<DrawerMenuItem>,
    headerContent: (@Composable () -> Unit)? = null,
    onMenuClick: (DrawerMenuItem) -> Unit
) {
    ModalDrawerSheet(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            headerContent?.invoke()

            Spacer(modifier = Modifier.height(12.dp))

            menuItems.forEach { item ->
                NavigationDrawerItem(
                    label = {
                        Text(
                            text = item.title,
                            style = MaterialTheme.typography.labelLarge
                        )
                    },
                    selected = currentItem == item,
                    onClick = {
                        onMenuClick(item)
                    },
                    modifier = Modifier.padding(NavigationDrawerItemDefaults.ItemPadding)
                )
            }
        }
    }
}

/**
 * Default drawer header composable with app branding
 *
 * @param modifier Modifier to be applied to the header
 * @param appIcon Optional composable for the app icon/logo
 * @param backgroundColor Background color for the header
 */
@Composable
internal fun DefaultDrawerHeader(
    modifier: Modifier = Modifier,
    appIcon: @Composable () -> Unit,
    backgroundColor: Color = MaterialTheme.colorScheme.primaryContainer
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .background(backgroundColor)
            .padding(24.dp)
            .fillMaxWidth()
    ) {
        appIcon()
    }
}

/**
 * Complete modal navigation drawer wrapper that handles state and gestures
 *
 * @param modifier Modifier to be applied to the drawer
 * @param drawerState State of the drawer (open/closed)
 * @param currentItem The currently selected destination
 * @param menuItems List of menu items to display
 * @param headerContent Optional custom header content
 * @param gesturesEnabled Whether swipe gestures are enabled
 * @param onMenuClick Callback when a menu item is clicked
 * @param content The main screen content
 */
@Composable
internal fun CommonNavigationDrawer(
    modifier: Modifier = Modifier,
    drawerState: DrawerState = rememberDrawerState(initialValue = DrawerValue.Closed),
    currentItem: DrawerMenuItem?,
    menuItems: List<DrawerMenuItem>,
    headerContent: (@Composable () -> Unit)? = null,
    gesturesEnabled: Boolean = true,
    onMenuClick: (DrawerMenuItem) -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        drawerContent = {
            AppDrawerContent(
                currentItem = currentItem,
                menuItems = menuItems,
                headerContent = headerContent,
                onMenuClick = { destination ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onMenuClick(destination)
                }
            )
        },
        content = content
    )
}
