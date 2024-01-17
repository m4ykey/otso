plugins {
    Plugins.apply {
        id(androidLibrary)
        id(kotlinAndroid)
        id(ksp)
        id(hiltAndroid)
    }
}

android {
    namespace = "com.m4ykey.data"
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
        buildConfig = true
    }
}

dependencies {

    implementation(project(":core"))

    implementation(Dependencies.AndroidX.Core.core)
    implementation(Dependencies.AndroidX.Core.appcompat)
    implementation("androidx.test.ext:junit-ktx:1.1.5")
    androidTestImplementation("androidx.test:core-ktx:1.5.0")
    androidTestImplementation("androidx.test:runner:1.5.2")
    implementation("com.squareup.moshi:moshi-kotlin-codegen:1.15.0")

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)

    implementation(Dependencies.Network.Retrofit.retrofit)
    implementation(Dependencies.Network.Retrofit.moshiConverter)
    implementation(Dependencies.Network.Moshi.moshiKotlin)

    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Coroutines.coroutinesAndroid)

    implementation(Dependencies.AndroidX.Paging.paging)
    implementation(Dependencies.AndroidX.Paging.pagingCompose)

    implementation(Dependencies.AndroidX.DataStore.preferences)

    implementation(Dependencies.Network.OkHttp.loggingInterceptor)

    androidTestImplementation(Dependencies.Test.TestImplementation.mockWebServer)
    androidTestImplementation(Dependencies.Test.TestImplementation.truth)

}