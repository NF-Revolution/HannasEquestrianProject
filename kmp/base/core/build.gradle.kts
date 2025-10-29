import com.codingfeline.buildkonfig.compiler.FieldSpec.Type.STRING
import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension.loadProperties
import com.nfrevolution.hannasequestrianproject.com.nfrevolution.hannasequestrianproject.extension.propertyString
import com.nfrevolution.hannasequestrianproject.extension.commonDependencies

plugins {
    alias(libs.plugins.hannasequestrianproject.kmp.library)
    alias(libs.plugins.hannasequestrianproject.kmp.compose)
    alias(libs.plugins.buildkonfig)
}

kotlin {
    sourceSets {
        commonDependencies {
            implementation(compose.runtime)
            implementation(compose.material3)
        }
    }
}

buildkonfig {
    packageName = "com.nfrevolution.hannasequestrianproject.core"
    objectName = "AppConfig"
    exposeObjectWithName = "AppConfig"

    val properties = loadProperties("$rootDir/local.properties")

    defaultConfigs {
        buildConfigField(STRING, "instagramUrl", properties.propertyString("INSTAGRAM_URL"))
        buildConfigField(STRING, "youtubeUrl", properties.propertyString("YOUTUBE_URL"))
        buildConfigField(STRING, "tiktokUrl", properties.propertyString("TIKTOK_URL"))
        buildConfigField(STRING, "storageUrl", properties.propertyString("STORAGE_URL"))
        buildConfigField(STRING, "databaseUrlHost", properties.propertyString("DATABASE_URL_HOST"))
    }
}
