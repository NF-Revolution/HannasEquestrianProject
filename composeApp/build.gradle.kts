import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension.applyKsp
import com.nfrevolution.hannasequestrianproject.extension.applyKspSourcesLocation
import com.nfrevolution.hannasequestrianproject.extension.applyTargets
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.commonTestDependencies

plugins {
    alias(libs.plugins.kotlin.multiplatform)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.google.ksp)

}

kotlin {
    applyTargets()
    applyKspSourcesLocation()

    sourceSets {
        commonDependencies {
            {
                implementation(compose.runtime)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.ui)
                implementation(compose.components.resources)
                implementation(compose.components.uiToolingPreview)
                implementation(libs.androidx.lifecycle.runtimeCompose)
            }
            commonTestDependencies {
                implementation(libs.kotlin.test)
            }
        }
    }
}

applyKsp()
