rootProject.name = "HannasEquestrianProject"
enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

pluginManagement {
    includeBuild("build-logic")
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
    }
}

dependencyResolutionManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
            }
        }
        mavenCentral()
    }
}

plugins {
    id("org.gradle.toolchains.foojay-resolver-convention") version "1.0.0"
}

include(":composeApp")

// KMP Base Modules
include(":kmp:base:core")
include(":kmp:base:navigation")

// KMP Compose Modules
include(":kmp:compose:theme")
include(":kmp:compose:foundation")
include(":kmp:compose:resources")

// KMP Feature Modules
include(":kmp:features:root")

include(":kmp:features:home:domain")
include(":kmp:features:home:presentation")

include(":kmp:features:horses:domain")
include(":kmp:features:horses:presentation")

include(":kmp:features:stables:domain")
include(":kmp:features:stables:presentation")

include(":kmp:features:about:domain")
include(":kmp:features:about:presentation")

include(":kmp:features:mainContent:domain")
include(":kmp:features:mainContent:presentation")

include(":kmp:features:navigationDrawer:domain")
include(":kmp:features:navigationDrawer:presentation")