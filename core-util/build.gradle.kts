import java.util.Properties
import java.io.FileInputStream

@Suppress("DSL_SCOPE_VIOLATION")
plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.kapt)
    alias(libs.plugins.parcelize)
}

val localProperties = Properties()
localProperties.load(FileInputStream(File(rootProject.projectDir, "local.properties")))

android {
    namespace = "com.nimdokai.pet.core.util"
    compileSdk = 33

    defaultConfig {
        minSdk = 21

        testInstrumentationRunner = "com.nimdokai.pet.core.testing.HiltTestRunner"
        consumerProguardFiles("consumer-rules.pro")

        // Please make sure to add API KEY to your `local.properties` file
        // cat_api_key="API_KEY_HERE"
        buildConfigField("String", "CAT_API_KEY", localProperties["cat_api_key"] as String)

    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    implementation(project(":core-resources"))
    // Arch Components
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)
    implementation(libs.kotlinx.viewbinding)
    implementation(libs.androidx.navigation.runtime.ktx)
    implementation(libs.androidx.navigation.fragment.ktx)
    implementation(libs.androidx.navigation.ui.ktx)

    implementation(libs.kotlinx.coroutines.android)
    implementation(libs.retrofit)
    implementation(libs.retrofit.gson.converter)

    // Local tests: jUnit, coroutines, Android runner
    testImplementation(libs.junit)
    testImplementation(libs.kotlinx.coroutines.test)
}
