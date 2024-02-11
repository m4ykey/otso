import org.jlleitschuh.gradle.ktlint.reporter.ReporterType

// Top-level build file where you can add configuration options common to all sub-projects/modules.
plugins {
    Plugins.apply {
        id(androidApplication) version Version.application apply false
        id(kotlinAndroid) version Version.kotlinAndroid apply false
        id(androidLibrary) version Version.androidLibrary apply false
        id(ksp) version Version.ksp apply false
        id(hiltAndroid) version Version.hilt apply false
        id(ktlint) version Version.ktlintGradle
        id(googleServices) version Version.gms apply false
        id(crashlytics) version Version.firebaseGradle apply false
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