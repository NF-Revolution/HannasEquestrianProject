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
            api(libs.tiamat)

            implementation(compose.ui)
            implementation(compose.animation)
            implementation(libs.compose.ui.backhandler)
        }
    }
}
