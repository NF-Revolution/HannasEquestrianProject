import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.kotlin.serialization)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(projects.kmp.base.core)

            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.contentnegotiation)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.kotlinx.serialization.json)
            implementation(libs.kotlinx.coroutines.core)
            implementation(libs.ktor.client.cio)

            implementation(libs.koin.core)
            implementation(libs.koin.annotations)
        }
    }
}
