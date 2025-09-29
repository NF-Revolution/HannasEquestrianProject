package com.nfrevolution.hannasequestrianproject.extension

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension
import org.jetbrains.kotlin.gradle.plugin.KotlinDependencyHandler

fun KotlinMultiplatformExtension.applyTargets() {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
}

fun KotlinMultiplatformExtension.commonDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.commonMain.dependencies { configure() }

fun KotlinMultiplatformExtension.commonTestDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.commonTest.dependencies { configure() }


fun KotlinMultiplatformExtension.webDependencies(
    configure: KotlinDependencyHandler.() -> Unit,
) = sourceSets.wasmJsMain.dependencies { configure() }

fun KotlinMultiplatformExtension.applyKspSourcesLocation() = sourceSets.commonMain {
    kotlin.srcDir("build/generated/ksp/metadata/commonMain/kotlin")
}