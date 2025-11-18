import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {

    sourceSets {
        commonDependencies {
            implementation(projects.kmp.base.core)

            api(libs.tiamat)

            implementation(compose.foundation)
            implementation(compose.ui)
        }
    }
}
