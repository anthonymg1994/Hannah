apply(from = rootProject.file("gradle/scripts/versioning.gradle.kts"))

plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
    alias(libs.plugins.detekt)
    alias(libs.plugins.hilt)
    kotlin("kapt")
}

android {
    namespace = "com.hannah.hannah"
    compileSdk = 36

    defaultConfig {
        applicationId = "com.hannah.hannah"
        minSdk = 24
        targetSdk = 36
        versionCode = project.extra["VERSION_CODE"].toString().toInt()
        versionName = project.extra["VERSION_NAME"].toString()
        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
    }

    buildTypes {
        release {
            isMinifyEnabled = false
            proguardFiles(
                getDefaultProguardFile("proguard-android-optimize.txt"),
                "proguard-rules.pro"
            )
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
    kotlinOptions {
        jvmTarget = "11"
    }
    buildFeatures {
        compose = true
    }
}

afterEvaluate {
    tasks.named("assembleRelease").configure {
        dependsOn(tasks.named("incrementVersion"))
    }

    tasks.withType<io.gitlab.arturbosch.detekt.Detekt>().configureEach {}

    tasks.named("check").configure {
        dependsOn(tasks.withType<io.gitlab.arturbosch.detekt.Detekt>())
    }
}

dependencies {
    implementation(libs.hilt.android)
    kapt(libs.hilt.compiler)

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)


    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)

    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)
}
