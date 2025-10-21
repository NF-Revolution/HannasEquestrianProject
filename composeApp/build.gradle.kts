import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.jvmDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(projects.kmp.base.core)
            implementation(projects.kmp.features.root)
            implementation(compose.runtime)
            implementation(compose.foundation)
            implementation(compose.material3)
            implementation(compose.ui)
            implementation(compose.components.resources)
            implementation(compose.components.uiToolingPreview)
            implementation(libs.androidx.lifecycle.runtimeCompose)
            implementation(libs.kotlinx.coroutines.core)
        }

        jvmDependencies {
            implementation(libs.kotlinx.coroutinesSwing)
            implementation(compose.desktop.currentOs)
        }
    }
}

compose.desktop {
    application {
        mainClass = "com.nfrevolution.hannasequestrianproject.MainKt"

        nativeDistributions {
            packageName = "package"
            packageVersion = "1.0.0"
        }
    }
}