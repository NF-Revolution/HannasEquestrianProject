package com.nfrevolution.hannasequestrianproject.extension

import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun KotlinMultiplatformExtension.webTestDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.webTest.dependencies { configure() }

fun KotlinMultiplatformExtension.commonDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.commonMain.dependencies { configure() }

fun KotlinMultiplatformExtension.webDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.wasmJsMain.dependencies { configure() }

fun KotlinMultiplatformExtension.jvmDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.jvmMain.dependencies { configure() }
