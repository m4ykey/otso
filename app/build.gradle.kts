plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
    id("com.google.gms.google-services")
    id("com.google.firebase.crashlytics")
    id("com.google.devtools.ksp")
    id("com.google.dagger.hilt.android")
}

android {
    namespace = "com.m4ykey.otso"
    compileSdk = Version.compileSdk

    defaultConfig {
        applicationId = "com.m4ykey.otso"
        minSdk = Version.minSdk
        targetSdk = Version.targetSdk
        versionCode = Version.versionCode
        versionName = Version.versionName

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        vectorDrawables {
            useSupportLibrary = true
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
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
    packaging {
        resources {
            excludes += "/META-INF/{AL2.0,LGPL2.1}"
        }
    }
}

dependencies {

    implementation(project(":navigation"))

    implementation(Dependencies.AndroidX.Core.core)

    implementation(Dependencies.AndroidX.Compose.composeActivity)
    implementation(platform(Dependencies.AndroidX.Compose.composeBom))
    implementation(Dependencies.AndroidX.Compose.composeUi)
    implementation(Dependencies.AndroidX.Compose.composeMaterial3)

    implementation(Dependencies.Firebase.crashlytics)
    implementation(Dependencies.Firebase.analytics)

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.composeUiTestJunit4)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)
    androidTestImplementation(platform(Dependencies.AndroidX.Compose.composeBom))
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)

    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Coil.coil)

    implementation(Dependencies.AndroidX.Compose.composeNavigation)

    implementation(Dependencies.Firebase.messaging)

    implementation(Dependencies.Accompanist.permissions)

}