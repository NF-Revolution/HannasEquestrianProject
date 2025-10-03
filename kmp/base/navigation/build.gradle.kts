import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {

    sourceSets {
        webDependencies {
            api(libs.tiamat)

            implementation(compose.ui)
            implementation(compose.animation)
            implementation(libs.compose.ui.backhandler)
        }
    }
}
