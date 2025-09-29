package com.nfrevolution.hannasequestrianproject

import com.nfrevolution.hannasequestrianproject.internal.composeCompiler
import com.nfrevolution.hannasequestrianproject.internal.composeCompilerPluginId
import com.nfrevolution.hannasequestrianproject.internal.jetbrainsComposePluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply
import org.gradle.kotlin.dsl.assign

class KmpComposePlugin : Plugin<Project> {

    override fun apply(target: Project) = with(target) {
        apply(plugin = jetbrainsComposePluginId)
        apply(plugin = composeCompilerPluginId)

        composeCompiler {
            if (project.hasProperty("enableComposeCompilerReports")) {
                val outPath = project.layout.buildDirectory.dir("compose_metrics").get().asFile

                reportsDestination.set(outPath)
                metricsDestination.set(outPath)
            }
            stabilityConfigurationFiles.add {
                rootDir.resolve("config/compose-stability.config")
            }
            includeSourceInformation = true
        }
    }
}
