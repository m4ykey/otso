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
    namespace = "com.m4ykey.remote"
    compileSdk = Version.compileSdk

    defaultConfig {
        minSdk = Version.minSdk

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer-rules.pro")
    }

    val apiKey by lazy {
        rootProject.file("local.properties").inputStream().use { input ->
            Properties().apply { load(input) }
        }
    }

    buildTypes.all {
        buildConfigField("String", "NEWS_API_KEY", "\"${apiKey.getProperty("NEWS_API_KEY")}\"")
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

    implementation(Dependencies.AndroidX.Paging.paging)
    implementation(Dependencies.AndroidX.Paging.pagingCompose)

    testImplementation(Dependencies.Test.TestImplementation.junit)

    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.testExtJunit)
    androidTestImplementation(Dependencies.Test.AndroidTestImplementation.espresso)

    implementation(Dependencies.Hilt.hiltAndroid)
    ksp(Dependencies.Hilt.hiltCompiler)

    implementation(Dependencies.Coroutines.coroutinesAndroid)

    implementation(Dependencies.Network.Retrofit.retrofit)
    implementation(Dependencies.Network.Retrofit.moshiConverter)
    implementation(Dependencies.Network.OkHttp.loggingInterceptor)
    implementation(Dependencies.Network.OkHttp.okHttp)

}