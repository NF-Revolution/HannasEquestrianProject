import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
}

kotlin {
    sourceSets {
        webDependencies {
            implementation(projects.kmp.xxx.domain)
        }
    }
}
