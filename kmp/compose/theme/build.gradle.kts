import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(projects.kmp.compose.resources)
            implementation(compose.material3)
            implementation(compose.components.resources)
        }
    }
}
