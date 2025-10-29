import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
        }
    }
}
