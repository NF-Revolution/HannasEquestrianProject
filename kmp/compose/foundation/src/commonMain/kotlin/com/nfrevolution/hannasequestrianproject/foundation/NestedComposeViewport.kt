package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.foundation.layout.BoxScope
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
public expect fun NestedComposeViewport(
    id: String = "nested-compose-viewport",
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
)
