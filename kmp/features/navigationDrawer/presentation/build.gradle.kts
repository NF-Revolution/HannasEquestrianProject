import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {

    sourceSets {
        webDependencies {
            implementation(projects.kmp.features.navigationDrawer.domain)
            implementation(projects.kmp.base.navigation)
            implementation(projects.kmp.compose.foundation)
            implementation(projects.kmp.compose.theme)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)

            implementation(libs.flowmvi.core)
            implementation(libs.flowmvi.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
            implementation(libs.koin.viewmodel)
        }
    }
}
