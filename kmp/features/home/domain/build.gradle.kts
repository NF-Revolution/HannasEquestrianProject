import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.jvmTestDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
        }

        jvmTestDependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutines.test)
            implementation(libs.mockk.core)
        }
    }
}
