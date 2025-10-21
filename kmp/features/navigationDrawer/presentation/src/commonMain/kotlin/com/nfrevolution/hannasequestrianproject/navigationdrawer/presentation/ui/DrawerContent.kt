package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.Copyright
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerMenuItem
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.SocialMediaItem

@Composable
internal fun DrawerContent(
    modifier: Modifier = Modifier,
    currentItem: DrawerMenuItem?,
    menuItems: List<DrawerMenuItem>,
    socialItems: List<SocialMediaItem> = emptyList(),
    copyright: Copyright,
    onMenuClick: (DrawerMenuItem) -> Unit,
    onLogoClick: () -> Unit
) {
    ModalDrawerSheet(
        modifier = modifier,
        drawerContainerColor = MaterialTheme.colorScheme.surfaceVariant,
        drawerContentColor = MaterialTheme.colorScheme.onSurfaceVariant
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            Logo(onLogoClick)
            HorizontalDivider()
            menuItems.forEach { item ->
                DrawerMenuItemRow(
                    item = item,
                    isSelected = currentItem == item,
                    onClick = { onMenuClick(item) }
                )
            }
            Spacer(modifier = Modifier.weight(1f))
            if (socialItems.isNotEmpty()) {
                SocialsContent(socialItems = socialItems)
            }
            CopyrightContent(copyright = copyright)
        }
    }
}
