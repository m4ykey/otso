object Dependencies {

    object AndroidX {
        object Compose {
            const val composeBom = "androidx.compose:compose-bom:${Version.composeBom}"
            const val composeUi = "androidx.compose.ui:ui"
            const val composeUiGraphics = "androidx.compose.ui:ui-graphics"
            const val composeUiToolingPreview = "androidx.compose.ui:ui-tooling-preview"
            const val composeMaterial3 = "androidx.compose.material3:material3-android:${Version.material3}"
            const val composeActivity = "androidx.activity:activity-compose:${Version.composeActivity}"
            const val composeNavigation = "androidx.navigation:navigation-compose:${Version.navigation}"
            const val composeIconsExtended = "androidx.compose.material:material-icons-extended:${Version.iconsExtended}"
            const val composeConstraintLayout = "androidx.constraintlayout:constraintlayout-compose:${Version.constraintLayoutCompose}"
        }
        object Core {
            const val core = "androidx.core:core-ktx:${Version.core}"
            const val appcompat = "androidx.appcompat:appcompat:${Version.appcompat}"
            const val material = "com.google.android.material:material:${Version.material}"
        }
        object Lifecycle {
            const val livedata = "androidx.lifecycle:lifecycle-livedata-ktx:${Version.lifecycle}"
            const val runtimeLivedata = "androidx.compose.runtime:runtime-livedata:1.6.1"
            const val runtime = "androidx.lifecycle:lifecycle-runtime-ktx:${Version.lifecycle}"
            const val viewModel = "androidx.lifecycle:lifecycle-viewmodel-compose:${Version.lifecycle}"
        }

        object DataStore {
            const val preferences = "androidx.datastore:datastore-preferences:${Version.datastore}"
        }
        object Paging {
            const val paging = "androidx.paging:paging-runtime-ktx:${Version.paging}"
            const val pagingCompose = "androidx.paging:paging-compose:${Version.paging}"
        }
    }

    object Firebase {
        const val crashlytics = "com.google.firebase:firebase-crashlytics:${Version.crashlytics}"
        const val analytics = "com.google.firebase:firebase-analytics:${Version.analytics}"
        const val messaging = "com.google.firebase:firebase-messaging:${Version.messaging}"
    }

    object Test {
        object TestImplementation {
            const val junit = "junit:junit:${Version.junit}"
            const val mockWebServer = "com.squareup.okhttp3:mockwebserver:${Version.mockWebServer}"
            const val truth = "com.google.truth:truth:${Version.truth}"
        }
        object DebugImplementation {
            const val composeUiTooling = "androidx.compose.ui:ui-tooling"
            const val composeUiTestManifest = "androidx.compose.ui:ui-test-manifest"
        }
        object AndroidTestImplementation {
            const val espresso = "androidx.test.espresso:espresso-core:${Version.espresso}"
            const val composeUiTestJunit4 = "androidx.compose.ui:ui-test-junit4"
            const val testExtJunit = "androidx.test.ext:junit:${Version.extJunit}"
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
        }
        object OkHttp {
            const val okHttp = "com.squareup.okhttp3:okhttp:${Version.okHttp}"
            const val loggingInterceptor = "com.squareup.okhttp3:logging-interceptor:${Version.okHttp}"
        }
    }

    object Hilt {
        const val hiltAndroid = "com.google.dagger:hilt-android:${Version.hilt}"
        const val hiltCompiler = "com.google.dagger:hilt-android-compiler:${Version.hilt}"
        const val hiltNavigation = "androidx.hilt:hilt-navigation-compose:${Version.hiltNavigation}"
    }

    object Coroutines {
        const val coroutinesAndroid = "org.jetbrains.kotlinx:kotlinx-coroutines-android:${Version.coroutines}"
    }

    object Coil {
        const val coil = "io.coil-kt:coil-compose:${Version.coil}"
    }

}