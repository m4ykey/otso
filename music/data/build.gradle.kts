import java.util.Properties

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

    val apiKeys by lazy {
        rootProject.file("local.properties").inputStream().use { input ->
            Properties().apply { load(input) }
        }
    }

    buildTypes.all {
        buildConfigField("String", "SPOTIFY_CLIENT_ID", "\"${apiKeys.getProperty("SPOTIFY_CLIENT_ID")}\"")
        buildConfigField("String", "SPOTIFY_CLIENT_SECRET", "\"${apiKeys.getProperty("SPOTIFY_CLIENT_SECRET")}\"")
        buildConfigField("String", "YOUTUBE_API_KEY", "\"${apiKeys.getProperty("YOUTUBE_API_KEY")}\"")
        buildConfigField("String", "MUSIXMATCH_API_KEY", "\"${apiKeys.getProperty("MUSIXMATCH_API_KEY")}\"")
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

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)
    androidTestImplementation(Dependencies.Test.TestImplementation.mockWebServer)
    androidTestImplementation(Dependencies.Test.TestImplementation.truth)

    implementation(Dependencies.Network.Retrofit.retrofit)
    implementation(Dependencies.Network.Retrofit.moshiConverter)
    implementation(Dependencies.Network.Moshi.moshiKotlin)
    implementation(Dependencies.Network.OkHttp.loggingInterceptor)

    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Coroutines.coroutinesAndroid)

    implementation(Dependencies.AndroidX.Paging.paging)
    implementation(Dependencies.AndroidX.Paging.pagingCompose)
    implementation(Dependencies.AndroidX.DataStore.preferences)

}