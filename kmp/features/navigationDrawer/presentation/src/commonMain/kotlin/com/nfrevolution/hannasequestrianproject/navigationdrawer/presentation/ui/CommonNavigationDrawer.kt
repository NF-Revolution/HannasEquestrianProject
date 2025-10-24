package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.ui

import androidx.compose.material3.DrawerState
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.Copyright
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerMenuItem
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.SocialMediaItem
import kotlinx.coroutines.launch


/**
 * Complete modal navigation drawer wrapper that handles state and gestures
 *
 * @param modifier Modifier to be applied to the drawer
 * @param drawerState State of the drawer (open/closed)
 * @param currentItem The currently selected destination
 * @param menuItems List of menu items to display
 * @param socialItems List of social media items to display
 * @param copyright Copyright text to display
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
    socialItems: List<SocialMediaItem>,
    copyright: Copyright,
    gesturesEnabled: Boolean = true,
    onMenuClick: (DrawerMenuItem) -> Unit,
    onLogoClick: () -> Unit,
    content: @Composable () -> Unit
) {
    val coroutineScope = rememberCoroutineScope()

    ModalNavigationDrawer(
        modifier = modifier,
        drawerState = drawerState,
        gesturesEnabled = gesturesEnabled,
        scrimColor = MaterialTheme.colorScheme.scrim.copy(alpha = 0.6f),
        drawerContent = {
            DrawerContent(
                currentItem = currentItem,
                menuItems = menuItems,
                socialItems = socialItems,
                copyright = copyright,
                onMenuClick = { destination ->
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onMenuClick(destination)
                },
                onLogoClick = {
                    coroutineScope.launch {
                        drawerState.close()
                    }
                    onLogoClick()
                },
            )
        },
        content = content
    )
}
