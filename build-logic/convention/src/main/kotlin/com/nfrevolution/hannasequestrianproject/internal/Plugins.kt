package com.nfrevolution.hannasequestrianproject.internal

import org.gradle.api.Project

internal val Project.kotlinMultiplatformPluginId
    get() = libs.plugins.kotlin.multiplatform.get().pluginId

internal val Project.jetbrainsComposePluginId
    get() = libs.plugins.jetbrains.compose.multiplatform.get().pluginId

internal val Project.composeCompilerPluginId
    get() = libs.plugins.jetbrains.compose.compiler.get().pluginId

internal val Project.kspPluginId
    get() = libs.plugins.google.ksp.get().pluginId