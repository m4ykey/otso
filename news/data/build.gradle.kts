plugins {
    Plugins.apply {
        id(androidLibrary)
        id(kotlinAndroid)
        id(ksp)
        id(hiltAndroid)
    }
}

android {
    namespace = "com.m4ykey.remote"
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
}

dependencies {

    implementation(project(":core"))

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Core.appcompat)

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)

    implementation(Dependencies.Network.Retrofit.retrofit)
    implementation(Dependencies.Network.Retrofit.moshiConverter)

    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Coroutines.coroutinesAndroid)

    implementation(Dependencies.AndroidX.Paging.paging)
    implementation(Dependencies.AndroidX.Paging.pagingCompose)

}