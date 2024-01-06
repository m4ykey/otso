import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

buildscript {
    dependencies {
        classpath("${Plugins.buildGradle}:${Version.toolsBuildGradle}")
        classpath("${Plugins.gms}:${Version.gms}")
        classpath("${Plugins.crashlyticsGradle}:${Version.firebaseGradle}")
    }
}
// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    Plugins.apply {
        id(androidApplication) version Version.application apply false
        id(kotlinAndroid) version Version.kotlinAndroid apply false
        id(androidLibrary) version Version.androidLibrary apply false
        id(ksp) version Version.ksp apply false
        id(hiltAndroid) version Version.hilt apply false
        id(ktlint) version Version.ktlintGradle
    }
}

subprojects {
    apply(plugin = Plugins.ktlint)

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