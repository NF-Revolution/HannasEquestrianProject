import com.github.benmanes.gradle.versions.updates.DependencyUpdatesTask

plugins {
    alias(libs.plugins.jetbrains.compose.compiler) apply false
    alias(libs.plugins.jetbrains.compose.multiplatform) apply false
    alias(libs.plugins.firebase.crashlytics) apply false
    alias(libs.plugins.google.services) apply false
    alias(libs.plugins.kotlin.multiplatform) apply false
    alias(libs.plugins.kotlin.serialization) apply false
    alias(libs.plugins.google.ksp) apply false
    alias(libs.plugins.secrets) apply false
    alias(libs.plugins.kotlin.cocoapods) apply false
    alias(libs.plugins.gradle.versions) apply false
}

allprojects {
    apply(plugin = "com.github.ben-manes.versions")
}

tasks {
    withType<DependencyUpdatesTask> {
        rejectVersionIf {
            isNonStable(candidate.version) && !isNonStable(currentVersion)
        }
    }

    register("checkDependencyUpdates", Exec::class) {
        group = "help"
        description = "Check for dependency updates without parallel execution"
        commandLine("./gradlew", "dependencyUpdates", "--no-parallel")
    }
}

fun isNonStable(version: String): Boolean {
    val stableKeyword = listOf("RELEASE", "FINAL", "GA").any { version.uppercase().contains(it) }
    val regex = "^[0-9,.v-]+(-r)?$".toRegex()
    val isStable = stableKeyword || regex.matches(version)
    return isStable.not()
}