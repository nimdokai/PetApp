plugins {
    `kotlin-dsl`
}

java {
    toolchain {
        languageVersion.set(JavaLanguageVersion.of(11))
    }
}

dependencies {
//    compileOnly(libs.android.gradlePlugin)
//    compileOnly(libs.kotlin.gradlePlugin)
}

// TODO https://github.com/chrisbanes/tivi/commit/efbe3e4f80cae38042c3f6b0ed2472443ff49428
// TODO https://medium.com/adessoturkey/modern-dependency-management-gradle-version-catalogs-and-convention-plugins-c9a822aa270e

gradlePlugin {
    plugins {

        register("androidApplication") {
            id = "com.nimdokai.android.application"
            implementationClass = "com.nimdokai.gradle.AndroidApplicationConventionPlugin"
        }

        register("androidLibrary") {
            id = "com.nimdokai.android.library"
            implementationClass = "com.nimdokai.gradle.AndroidLibraryConventionPlugin"
        }
//
//        register("androidTest") {
//            id = "app.tivi.android.test"
//            implementationClass = "app.tivi.gradle.AndroidTestConventionPlugin"
//        }
    }
}