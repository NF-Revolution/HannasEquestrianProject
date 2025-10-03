plugins {
    `kotlin-dsl`
}

group = "com.nfrevolution.hannasequestrianproject"

dependencies {
    compileOnly(libs.kotlin.gradle.plugin)
    compileOnly(libs.compose.compiler.plugin)

    compileOnly(files(libs.javaClass.superclass.protectionDomain.codeSource.location))
}

gradlePlugin {
    plugins {
        register("hannasequestrianproject.kmp.compose") {
            id = "hannasequestrianproject.kmp.compose"
            implementationClass = "com.nfrevolution.hannasequestrianproject.KmpComposePlugin"
        }
        register("hannasequestrianproject.kmp.library") {
            id = "hannasequestrianproject.kmp.library"
            implementationClass = "com.nfrevolution.hannasequestrianproject.KmpLibraryPlugin"
        }
    }
}
