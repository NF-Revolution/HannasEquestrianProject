package com.nfrevolution.hannasequestrianproject

import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension.applyKsp
import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.internal.applyKspSourcesLocation
import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.internal.applyTargets
import com.nfrevolution.hannasequestrianproject.internal.kmpExtension
import com.nfrevolution.hannasequestrianproject.internal.kotlinMultiplatformPluginId
import com.nfrevolution.hannasequestrianproject.internal.kspPluginId
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.apply

class KmpLibraryPlugin : Plugin<Project> {

    override fun apply(target: Project): Unit = with(target) {
        apply(plugin = kotlinMultiplatformPluginId)
        apply(plugin = kspPluginId)

        kmpExtension {
            explicitApi()
            applyTargets()
            applyKspSourcesLocation()
        }
        applyKsp()
    }
}
