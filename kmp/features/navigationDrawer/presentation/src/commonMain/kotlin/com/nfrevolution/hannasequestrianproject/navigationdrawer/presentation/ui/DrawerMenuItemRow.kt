package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationDrawerItem
import androidx.compose.material3.NavigationDrawerItemDefaults
import androidx.compose.material3.ShapeDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.DrawerMenuItem
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun DrawerMenuItemRow(
    item: DrawerMenuItem,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    NavigationDrawerItem(
        label = {
            Text(
                text = stringResource(item.title),
                style = MaterialTheme.typography.titleMedium
            )
        },
        selected = isSelected,
        onClick = onClick,
        shape = ShapeDefaults.ExtraSmall,
        colors = NavigationDrawerItemDefaults.colors(
            selectedContainerColor = MaterialTheme.colorScheme.onSurfaceVariant,
            unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
            selectedTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
        ),
    )
}
