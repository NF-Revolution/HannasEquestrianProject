import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    sourceSets {
        webDependencies {
            implementation(projects.kmp.compose.resources)
            implementation(compose.material3)
            implementation(compose.components.resources)
        }
    }
}
