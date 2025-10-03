import com.nfrevolution.hannasequestrianproject.extension.applyTargets
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.commonTestDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.base.core)
            implementation(projects.kmp.base.navigation)

            implementation(projects.kmp.compose.foundation)
            implementation(projects.kmp.compose.theme)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(libs.compose.ui.backhandler)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
        }

        commonTestDependencies {
            implementation(libs.kotlin.test)
        }
    }
}
