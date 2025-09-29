package com.nfrevolution.hannasequestrianproject.internal

import org.gradle.api.Project

internal val Project.applicationPluginId
    get() = libs.plugins.android.application.get().pluginId

internal val Project.kotlinPluginId
    get() = libs.plugins.kotlin.android.get().pluginId

internal val Project.kotlinMultiplatformPluginId
    get() = libs.plugins.kotlin.multiplatform.get().pluginId

internal val Project.libraryPluginId
    get() = libs.plugins.android.kotlin.multiplatform.library.get().pluginId

internal val Project.jetbrainsComposePluginId
    get() = libs.plugins.jetbrains.compose.multiplatform.get().pluginId

internal val Project.composeCompilerPluginId
    get() = libs.plugins.jetbrains.compose.compiler.get().pluginId