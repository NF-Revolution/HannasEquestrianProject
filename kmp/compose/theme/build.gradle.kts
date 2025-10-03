import com.nfrevolution.hannasequestrianproject.extension.applyTargets
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(compose.material3)
        }
    }
}
