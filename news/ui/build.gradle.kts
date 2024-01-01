plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.m4ykey.ui"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    buildTypes {
        release {
            isMinifyEnabled = true
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }
    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }
    kotlinOptions {
        jvmTarget = "17"
    }
    buildFeatures {
        compose = true
    }
    composeOptions {
        kotlinCompilerExtensionVersion = Version.kotlinCompilerExtension
    }
}

dependencies {

    implementation(project(":news:data"))
    implementation(project(":core"))

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Core.appcompat)
    implementation(Dependencies.AndroidX.Core.material)

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.composeUiTestJunit4)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)
    androidTestImplementation(platform(Dependencies.AndroidX.Compose.composeBom))
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)

    debugImplementation(Dependencies.Test.DebugImplementation.composeUiTestManifest)
    debugImplementation(Dependencies.Test.DebugImplementation.composeUiTooling)

    implementation(Dependencies.AndroidX.Compose.composeActivity)
    implementation(platform(Dependencies.AndroidX.Compose.composeBom))
    implementation(Dependencies.AndroidX.Compose.composeUi)
    implementation(Dependencies.AndroidX.Compose.composeUiGraphics)
    implementation(Dependencies.AndroidX.Compose.composeUiToolingPreview)
    implementation(Dependencies.AndroidX.Compose.composeMaterial3)

    implementation(Dependencies.Hilt.hiltAndroid)
    implementation(Dependencies.Hilt.hiltNavigation)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Coroutines.coroutinesAndroid)

    implementation(Dependencies.AndroidX.Lifecycle.runtime)
    implementation(Dependencies.AndroidX.Lifecycle.viewModel)

    implementation(Dependencies.Coil.coil)

    implementation(Dependencies.AndroidX.Paging.paging)
    implementation(Dependencies.AndroidX.Paging.pagingCompose)

}