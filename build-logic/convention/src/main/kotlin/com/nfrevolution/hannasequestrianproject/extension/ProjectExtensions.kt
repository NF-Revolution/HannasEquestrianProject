package com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension

import com.nfrevolution.hannasequestrianproject.internal.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

fun Project.applyKsp() {
    dependencies {
        add("kspCommonMainMetadata", libs.koin.ksp.compiler)
    }

    tasks.withType<KotlinCompilationTask<*>>().configureEach {
        if (name != "kspCommonMainKotlinMetadata") {
            dependsOn("kspCommonMainKotlinMetadata")
        }
    }
}