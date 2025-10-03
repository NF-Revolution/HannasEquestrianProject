import com.nfrevolution.hannasequestrianproject.extension.applyTargets
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
            implementation(compose.foundation)
            implementation(compose.material3)
        }

        webDependencies {
            implementation(compose.ui)
        }
    }
}
