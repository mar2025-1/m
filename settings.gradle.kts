pluginManagement {
    repositories {
        google {
            content {
                includeGroupByRegex("com\\.android.*")
                includeGroupByRegex("com\\.google.*")
                includeGroupByRegex("androidx.*")
                includeGroupByRegex("dagger.*")
            }
        }
        mavenCentral()
        gradlePluginPortal()
        maven { url = uri("https://jitpack.io") }
        maven { url = uri("https://repo1.maven.org/maven2/") }
    }

    plugins {
        id("com.android.application") version "8.9.1"
        id("org.jetbrains.kotlin.android") version "2.0.21"
        id("com.google.dagger.hilt.android") version "2.51.1"
        id("androidx.navigation.safeargs.kotlin") version "2.7.7"
    }

    resolutionStrategy {
        eachPlugin {
            if (requested.id.id == "com.google.dagger.hilt.android.plugin") {
                useModule("com.google.dagger:hilt-android-gradle-plugin:${requested.version}")
            }
        }
    }
}

dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
        maven { url = uri("https://jitpack.io") }
    }
}

rootProject.name = "mard"
include(":app")
