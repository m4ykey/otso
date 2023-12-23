plugins {
    id("com.android.library")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.m4ykey.navigation"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
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
}

dependencies {

    implementation(project(":music:ui"))
    implementation(project(":news:ui"))

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Compose.composeActivity)
    implementation(platform(Dependencies.AndroidX.Compose.composeBom))
    implementation(Dependencies.AndroidX.Compose.composeUi)
    implementation(Dependencies.AndroidX.Compose.composeMaterial3)
    implementation(Dependencies.AndroidX.Compose.composeNavigation)
    implementation(Dependencies.AndroidX.Compose.composeIconsExtended)

    testImplementation(Dependencies.Test.TestImplementation.junit)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)

}