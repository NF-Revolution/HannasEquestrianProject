package com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension

import com.nfrevolution.hannasequestrianproject.internal.libs
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.withType
import org.jetbrains.kotlin.gradle.tasks.KotlinCompilationTask

fun Project.applyKsp() {
    dependencies {
        add("kspWasmJs", libs.koin.ksp.compiler)
    }

    afterEvaluate {
        tasks.named("kspKotlinWasmJs").configure {
            dependsOn(tasks.matching {
                it.name.contains("generateResourceAccessors") ||
                        it.name.contains("generateActualResourceCollectors") ||
                        it.name.contains("generateExpectResourceCollectors") ||
                        it.name.contains("generateComposeResClass")
            })
        }

        tasks.withType<KotlinCompilationTask<*>>().configureEach {
            if (name != "kspKotlinWasmJs" && name.contains("WasmJs")) {
                dependsOn("kspKotlinWasmJs")
            }
        }
    }
}