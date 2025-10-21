import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(compose.runtime)
            implementation(compose.components.resources)
            implementation(compose.ui)
        }
    }
}

compose.resources {
    packageOfResClass = "com.nfrevolution.hannasequestrianproject.resources"
    generateResClass = always
    publicResClass = true
}
