@file:Suppress("UnstableApiUsage")

pluginManagement {
    repositories {
        google()
        mavenCentral()
        gradlePluginPortal()
    }
}
dependencyResolutionManagement {
    repositoriesMode.set(RepositoriesMode.FAIL_ON_PROJECT_REPOS)
    repositories {
        google()
        mavenCentral()
    }
}

rootProject.name = "otso"
include(":app")
include(":core")
include(":news:ui")
include(":news:data")
include(":music:ui")
include(":music:data")
include(":navigation")
include(":tools:ui")