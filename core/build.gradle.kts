plugins {
    Plugins.apply {
        id(androidLibrary)
        id(kotlinAndroid)
        id(ksp)
        id(hiltAndroid)
    }
}

android {
    namespace = "com.m4ykey.core"
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

    implementation(platform(Dependencies.AndroidX.Compose.composeBom))
    implementation(Dependencies.AndroidX.Compose.composeUi)
    implementation(Dependencies.AndroidX.Compose.composeUiGraphics)
    implementation(Dependencies.AndroidX.Compose.composeMaterial3)
    implementation(Dependencies.AndroidX.Compose.composeConstraintLayout)
    implementation(Dependencies.AndroidX.Lifecycle.viewModel)
    implementation(Dependencies.AndroidX.Lifecycle.runtime)

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)

    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Network.Moshi.moshiCodegen)
    implementation(Dependencies.Network.Moshi.moshiKotlin)
    implementation(Dependencies.Network.OkHttp.okHttp)
    implementation(Dependencies.Network.OkHttp.loggingInterceptor)
    implementation(Dependencies.Network.Retrofit.retrofit)
    implementation(Dependencies.Network.Retrofit.moshiConverter)

    implementation(Dependencies.Coil.coil)

}