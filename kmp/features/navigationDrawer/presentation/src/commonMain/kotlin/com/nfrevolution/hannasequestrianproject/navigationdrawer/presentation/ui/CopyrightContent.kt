package com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.ui

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.nfrevolution.hannasequestrianproject.navigationdrawer.presentation.model.Copyright
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun CopyrightContent(copyright: Copyright) {
    HorizontalDivider()
    Text(
        modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
        text = stringResource(copyright.titleRes, copyright.year),
        style = MaterialTheme.typography.bodyMedium,
    )
}
