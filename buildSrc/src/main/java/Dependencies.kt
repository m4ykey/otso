object Dependencies {

    object AndroidX {
        object Compose {
            const val composeBom = "androidx.compose:compose-bom:${Version.composeBom}"
            const val composeUi = "androidx.compose.ui:ui"
            const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
            const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
            const val composeMaterial3 = "androidx.compose.material3:material3"
            const val composeActivity = "androidx.activity:activity-compose:1.8.1"
            const val composeNavigationRuntime = "androidx.navigation:navigation-runtime-ktx:${Version.navigation}"
            const val composeNavigation = "androidx.navigation:navigation-compose:${Version.navigation}"
        }
        object Core {
            const val core = "androidx.core:core-ktx:1.12.0"
            const val appcompat = "androidx.appcompat:appcompat:1.6.1"
            const val material = "com.google.android.material:material:1.10.0"
        }
        object Lifecycle {
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifecycle}"
        }
        object Room {
            const val runtime = "androidx.room:room-runtime:${Version.room}"
            const val compiler = "androidx.room:room-compiler:${Version.room}"
            const val ktx = "androidx.room:room-ktx:${Version.room}"
            const val paging = "androidx.room:room-paging:${Version.room}"
        }
        object DataStore {
            const val preferences = "androidx.datastore:datastore-preferences:1.0.0"
        }
        object Paging {
            const val paging = "androidx.paging:paging-runtime-ktx:3.2.1"
            const val pagingCompose = "androidx.paging:paging-compose:3.2.1"
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

    object Network {
        object Retrofit {
            const val retrofit = "com.squareup.retrofit2:retrofit:${Version.retrofit}"
            const val moshiConverter = "com.squareup.retrofit2:converter-moshi:${Version.retrofit}"
        }
        object Moshi {
            const val moshiKotlin = "com.squareup.moshi:moshi-kotlin:${Version.moshi}"
            const val moshiCodegen = "com.squareup.moshi:moshi-kotlin-codegen:${Version.moshi}"
            const val moshi = "com.squareup.moshi:moshi:${Version.moshi}"
        }
        object OkHttp {
            const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
        }
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:1.1.0"
    }

    object Coroutines {
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
        const val coroutinesCore = "org.jetbrains.kotlinx:kotlinx-coroutines-core:${Version.coroutines}"
    }

    object Coil {
        const val coil = "io.coil-kt:coil-compose:2.5.0"
    }

}