import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
}

kotlin {
    sourceSets {
        webDependencies {
            implementation(projects.kmp.xxx.domain)

            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.annotations)
        }
    }
}
