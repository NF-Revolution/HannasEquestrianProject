import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    sourceSets {
        webDependencies {
            implementation(compose.runtime)
            implementation(compose.components.resources)
        }
    }
}

compose.resources {
    packageOfResClass = "com.nfrevolution.hannasequestrianproject.resources"
    generateResClass = always
    publicResClass = true
}
