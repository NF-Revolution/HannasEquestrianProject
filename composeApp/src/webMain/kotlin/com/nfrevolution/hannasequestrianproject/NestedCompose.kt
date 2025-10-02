package com.nfrevolution.hannasequestrianproject

/*
 * Copyright 2025 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.viewinterop.WebElementView
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import kotlinx.browser.window
import kotlinx.coroutines.delay
import org.w3c.dom.HTMLDivElement

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun NestedComposeViewportDemo() {
    var counterValue: Int by remember { mutableStateOf(0) }
    LaunchedEffect(Unit) {
        while (true) {
            delay(1000)
            counterValue += 1
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        VideoPlayer(
            modifier = Modifier.fillMaxSize(),
            urlOrUri = "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
            controls = false,
            autoPlay = true,
            loop = true,
            muted = false,
            posterUrl = null,
            minWidthPx = 640,
            minHeightPx = 360,
        )


        NestedComposeViewPort(
            modifier = Modifier.size(150.dp, 60.dp).padding(top = 8.dp).align(Alignment.TopCenter)
        ) {
//            Text(
//                modifier = Modifier.align(Alignment.TopStart),
//                text = "Counter: $counterValue"
//            )
            Button(
                modifier = Modifier.align(Alignment.Center),
                onClick = {
                    counterValue = 0
                }) {
                Text("Counter: $counterValue")
            }
        }

    }
}

@OptIn(ExperimentalComposeUiApi::class)
@Composable
private fun NestedComposeViewPort(
    id: String = "nested-compose-viewport",
    modifier: Modifier = Modifier.size(300.dp),
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