import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    dependencies {
        classpath("com.android.tools.build:gradle:${Version.toolsBuildGradle}")
        classpath("com.google.gms:google-services:${Version.gms}")
        classpath("com.google.firebase:firebase-crashlytics-gradle:${Version.firebaseGradle}")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    id("com.android.application") version Version.application apply false
    id("org.jetbrains.kotlin.android") version Version.kotlinAndroid apply false
    id("com.android.library") version Version.androidLibrary apply false
    id("com.google.devtools.ksp") version Version.ksp apply false
    id("com.google.dagger.hilt.android") version Version.hilt apply false
    id("org.jlleitschuh.gradle.ktlint") version Version.ktlintGradle
}

subprojects {
    apply(plugin = "org.jlleitschuh.gradle.ktlint")

    ktlint {
        version.set(Version.ktlint)
        android.set(true)
        verbose.set(true)
        reporters {
            reporter(ReporterType.HTML)
        }
        filter {
            exclude("**/build/**")
            include("**/*.kt")
        }
    }
}