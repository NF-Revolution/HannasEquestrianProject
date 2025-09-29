import com.nfrevolution.ynth.com.nfrevolution.ynth.extension.applyKsp
import com.nfrevolution.ynth.extension.androidDependencies
import com.nfrevolution.ynth.extension.applyKspSourcesLocation
import com.nfrevolution.ynth.extension.applyTargets
import com.nfrevolution.ynth.extension.commonDependencies
import com.nfrevolution.ynth.extension.iosDependencies
import com.nfrevolution.ynth.extension.webDependencies


plugins {
    alias(libs.plugins.ynth.kmp.library)
    alias(libs.plugins.google.ksp)
}

kotlin {
    applyTargets("com.nfrevolution.ynth.xxx")
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
