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