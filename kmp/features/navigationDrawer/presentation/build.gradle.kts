import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.jvmTestDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.navigationDrawer.domain)
            implementation(projects.kmp.base.navigation)
            implementation(projects.kmp.base.core)
            implementation(projects.kmp.compose.foundation)
            implementation(projects.kmp.compose.theme)
            implementation(projects.kmp.compose.resources)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)
            implementation(compose.components.resources)

            implementation(libs.flowmvi.core)
            implementation(libs.flowmvi.compose)

            implementation(libs.kotlinx.datetime)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
            implementation(libs.koin.viewmodel)
        }

        jvmTestDependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.flowmvi.test)
            implementation(libs.mockk.core)
        }
    }
}
