package com.nfrevolution.hannasequestrianproject.navigation

import androidx.compose.animation.ContentTransform
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.backhandler.PredictiveBackHandler
import com.composegears.tiamat.compose.Navigation
import com.composegears.tiamat.compose.TransitionController
import com.composegears.tiamat.compose.back
import com.composegears.tiamat.compose.navigationPlatformDefault
import com.composegears.tiamat.compose.rememberNavController
import com.composegears.tiamat.navigation.NavDestination
import kotlin.coroutines.cancellation.CancellationException

@Composable
@OptIn(ExperimentalComposeUiApi::class)
public fun HannasNavigation(
    modifier: Modifier = Modifier,
    isPredictiveBackEnabled: Boolean,
    startDestination: NavDestination<*>,
    destinations: Array<NavDestination<*>>,
) {
    val navController = rememberNavController(
        startDestination = startDestination,
    )

    BoxWithConstraints(modifier) {
        Navigation(
            navController = navController,
            modifier = Modifier.fillMaxSize(),
            destinations = destinations,
            contentTransformProvider = { navigationPlatformDefault(it) }
        )
        PredictiveBackHandler(isPredictiveBackEnabled) { progress ->
            val controller = TransitionController()
            navController.back(
                transition = ContentTransform(
                    targetContentEnter = slideInHorizontally(
                        animationSpec = tween(durationMillis = 350, easing = LinearEasing),
                        initialOffsetX = { -it / 3 }
                    ) + fadeIn(tween(100)),
                    initialContentExit = slideOutHorizontally(
                        animationSpec = tween(durationMillis = 350, easing = LinearEasing),
                        targetOffsetX = { it }
                    ),
                    sizeTransform = null,
                    targetContentZIndex = -1f
                ),
                transitionController = controller,
            )
            try {
                progress.collect { controller.update(it.progress) }
                controller.finish()
            } catch (_: CancellationException) {
                controller.cancel()
            }
        }
    }
}
