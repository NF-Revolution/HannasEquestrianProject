package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nfrevolution.hannasequestrianproject.resources.icons.HannasEquestrianProject
import com.nfrevolution.hannasequestrianproject.resources.icons.filled.JumpingHorseLogo

@Composable
internal fun Logo(onLogoClick: () -> Unit) {
    Box(
        modifier = Modifier
            .height(160.dp)
            .padding(20.dp)
            .fillMaxWidth()
            .clickable(
                interactionSource = null,
                indication = null,
                onClick = onLogoClick
            ),
        contentAlignment = Alignment.Center,
    ) {
        Icon(
            modifier = Modifier.fillMaxSize(),
            imageVector = HannasEquestrianProject.Filled.JumpingHorseLogo,
            contentDescription = "Jumping Horse Logo",
            tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
    }
}
