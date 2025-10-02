import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.commonTestDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.ksp)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonDependencies {
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
