import com.nfrevolution.hannasequestrianproject.extension.applyTargets
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
}

kotlin {
    applyTargets()

    sourceSets {
        commonDependencies {
        }
    }
}
