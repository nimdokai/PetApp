@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
}

android {
    namespace = "com.nimdokai.pet.core.testing"
    compileSdk = 34

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
        consumerProguardFiles("consumer.rules.pro")
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
    implementation(project(":core-util"))
    implementation(project(":core-resources"))
    implementation(project(":core-util"))
    implementation(project(":core-domain"))
    implementation(project(":core-network"))
    implementation(project(":core-data"))

    implementation(libs.androidx.test.runner)
    implementation(libs.kotlinx.coroutines.core)
    implementation(libs.kotlinx.coroutines.test)
    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.hilt.android.testing)
    implementation(libs.retrofit)
    implementation(libs.junit)
    implementation(libs.androidx.appcompat)

}