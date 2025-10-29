package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DrawerState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch

/**
 * Composable for creating a top app bar with a drawer menu button
 *
 * @param title The title to display in the app bar
 * @param drawerState The drawer state to control opening
 * @param modifier Modifier to be applied to the top app bar
 * @param containerColor Background color of the top app bar container
 * @param titleContainerColor Background color behind the title
 * @param actions Optional additional actions to display in the app bar
 */
@OptIn(ExperimentalMaterial3Api::class)
@Composable
public fun CommonTopBar(
    title: String,
    drawerState: DrawerState,
    modifier: Modifier = Modifier,
    containerColor: Color = Color.Transparent,
    titleContainerColor: Color? = null,
    actions: @Composable RowScope.() -> Unit = {}
) {
    val coroutineScope = rememberCoroutineScope()

    CenterAlignedTopAppBar(
        title = {
            val titleModifier = titleContainerColor?.let {
                Modifier
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(it)
            } ?: Modifier

            Text(
                modifier = titleModifier.padding(horizontal = 16.dp, vertical = 8.dp),
                text = title,
                fontStyle = FontStyle.Italic
            )
        },
        navigationIcon = {
            IconButton(
                onClick = {
                    coroutineScope.launch {
                        drawerState.open()
                    }
                },
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
            ) {
                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = "Open navigation drawer",
                )
            }
        },
        actions = actions,
        modifier = modifier,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = containerColor,
            titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
        )
    )
}
