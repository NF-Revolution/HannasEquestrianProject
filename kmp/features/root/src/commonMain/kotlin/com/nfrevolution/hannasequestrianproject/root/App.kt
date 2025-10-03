package com.nfrevolution.hannasequestrianproject.root

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.nfrevolution.hannasequestrianproject.theme.HannasTheme
import org.koin.compose.KoinApplication

@Composable
public fun App() {
    KoinApplication(
        application = {
            modules()
        }
    ) {
        HannasTheme {
            Surface(modifier = Modifier.fillMaxSize()) {
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text("Hanna's Equestrian Project")
                }
            }
        }
    }
}
