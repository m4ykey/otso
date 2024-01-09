plugins {
    Plugins.apply {
        id(androidLibrary)
        id(kotlinAndroid)
    }
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

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Core.appcompat)
    implementation(Dependencies.AndroidX.Core.material)

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)

    implementation(Dependencies.AndroidX.Compose.composeActivity)
    implementation(platform(Dependencies.AndroidX.Compose.composeBom))
    implementation(Dependencies.AndroidX.Compose.composeUi)
    implementation(Dependencies.AndroidX.Compose.composeUiGraphics)
    implementation(Dependencies.AndroidX.Compose.composeUiToolingPreview)
    implementation(Dependencies.AndroidX.Compose.composeMaterial3)

}