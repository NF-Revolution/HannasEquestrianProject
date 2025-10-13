import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {

    sourceSets {
        webDependencies {
            implementation(projects.kmp.base.core)
            implementation(projects.kmp.base.navigation)

            implementation(projects.kmp.compose.foundation)
            implementation(projects.kmp.compose.theme)

            implementation(projects.kmp.features.navigationDrawer.presentation)
            implementation(projects.kmp.features.home.presentation)
            implementation(projects.kmp.features.horses.presentation)
            implementation(projects.kmp.features.stables.presentation)
            implementation(projects.kmp.features.about.presentation)
            implementation(projects.kmp.features.mainContent.presentation)

            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.animation)
            implementation(compose.components.resources)
            implementation(libs.compose.ui.backhandler)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
        }
    }
}
