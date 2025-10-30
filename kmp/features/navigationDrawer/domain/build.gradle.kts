import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(projects.kmp.base.navigation)
        }
    }
}
