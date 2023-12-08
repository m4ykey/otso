object Dependencies {

    object AndroidX {

        object Compose {
            const val composeBom = "androidx.compose:compose-bom:${Version.composeBom}"
            const val composeUi = "androidx.compose.ui:ui"
            const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
            const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
            const val composeMaterial3 = "androidx.compose.material3:material3"
            const val composeActivity = "androidx.activity:activity-compose:1.8.1"
        }

        object Core {
            const val core = "androidx.core:core-ktx:1.12.0"
            const val appcompat = "androidx.appcompat:appcompat:1.6.1"
            const val material = "com.google.android.material:material:1.10.0"
        }

        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"
        }

    }

    object Firebase {
        const val crashlytics = "com.google.firebase:firebase-crashlytics:18.6.0"
        const val analytics = "com.google.firebase:firebase-analytics:21.5.0"
    }

    object Test {

        object TestImplementation {
            const val junit = "junit:junit:4.13.2"
        }

        object DebugImplementation {
            const val composeUiTooling = "androidx.compose.ui:ui-tooling"
            const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"
        }

        object AndroidTestImplementation {
            const val espresso = "androidx.test.espresso:espresso-core:3.5.1"
            const val composeUiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
            const val testExtJunit = "androidx.test.ext:junit:1.1.5"
        }

    }

}