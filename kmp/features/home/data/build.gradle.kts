import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.jvmTestDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(projects.kmp.features.home.domain)
            implementation(projects.kmp.base.network)
            implementation(projects.kmp.base.core)

            implementation(libs.ktor.client.core)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)

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
