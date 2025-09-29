import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension.applyKsp
import com.nfrevolution.hannasequestrianproject.extension.androidDependencies
import com.nfrevolution.hannasequestrianproject.extension.applyKspSourcesLocation
import com.nfrevolution.hannasequestrianproject.extension.applyTargets
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies
import com.nfrevolution.hannasequestrianproject.extension.iosDependencies
import com.nfrevolution.hannasequestrianproject.extension.webDependencies

plugins {
    alias(libs.plugins.ynth.kmp.library)
    alias(libs.plugins.google.ksp)
}

kotlin {
    applyTargets("com.nfrevolution.hannasequestrianproject.xxx.domain")
    applyKspSourcesLocation()

    sourceSets {
        commonDependencies {
        }

        iosDependencies {
        }

        androidDependencies {
        }

        webDependencies {
        }
    }
}

applyKsp()