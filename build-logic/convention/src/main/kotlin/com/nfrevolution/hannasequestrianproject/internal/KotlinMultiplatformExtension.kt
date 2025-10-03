package com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.internal

import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.KotlinMultiplatformExtension

internal fun KotlinMultiplatformExtension.applyKspSourcesLocation() = sourceSets.wasmJsMain {
    kotlin.srcDir("build/generated/ksp/wasmJs/wasmJsMain/kotlin")
}

internal fun KotlinMultiplatformExtension.applyTargets() {
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
        binaries.executable()
    }
}