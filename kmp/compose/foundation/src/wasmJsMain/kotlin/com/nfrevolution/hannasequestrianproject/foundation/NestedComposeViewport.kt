package com.nfrevolution.hannasequestrianproject.foundation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.viewinterop.WebElementView
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import org.w3c.dom.HTMLDivElement

@OptIn(ExperimentalComposeUiApi::class)
@Composable
public fun NestedComposeViewport(
    id: String = "nested-compose-viewport",
    modifier: Modifier = Modifier,
    content: @Composable BoxScope.() -> Unit
) {
    WebElementView(
        modifier = modifier,
        factory = {
            (document.createElement("div") as HTMLDivElement).apply {
                this.id = id
                style.apply {
                    background = "transparent"
                    display = "block"
                    width = "100%"
                    height = "100%"
                }

                // Use double requestAnimationFrame to ensure proper initialization
                window.requestAnimationFrame {
                    window.requestAnimationFrame {
                        ComposeViewport(this) {
                            Box(modifier = Modifier.fillMaxSize().drawBehind {
                                drawRect(
                                    color = Color.Transparent,
                                    blendMode = BlendMode.Clear
                                )
                            }) {
                                content()
                            }
                        }
                    }
                }
            }
        }
    )
}
