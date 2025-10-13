import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {

    sourceSets {
        webDependencies {
            implementation(projects.kmp.features.horses.domain)

            implementation(projects.kmp.base.core)
            implementation(projects.kmp.base.navigation)
            implementation(projects.kmp.compose.foundation)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.materialIconsExtended)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
        }
    }
}
