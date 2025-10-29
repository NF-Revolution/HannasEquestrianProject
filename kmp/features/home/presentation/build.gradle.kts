import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.home.domain)
            implementation(projects.kmp.features.home.data)
            implementation(projects.kmp.features.navigationDrawer.presentation)

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

            implementation(libs.coil.compose)
            implementation(libs.coil.network.ktor3)

            implementation(libs.flowmvi.core)
            implementation(libs.flowmvi.compose)

            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
            implementation(libs.koin.viewmodel)
        }
    }
}
