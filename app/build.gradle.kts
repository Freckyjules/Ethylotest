plugins {
    alias(libs.plugins.android.application)
}

android {
    namespace = "com.example.ethylotest"
    compileSdk = 35

    defaultConfig {
        applicationId = "com.example.ethylotest"
        minSdk = 24
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

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
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    applicationVariants.all {
        outputs.all {
            val appName = "Ethylotest"
            val version = versionName ?: "1.0"
            val newApkName = "$appName-v$version.apk"
            (this as com.android.build.gradle.internal.api.ApkVariantOutputImpl).outputFileName = newApkName
        }
    }
}

dependencies {
    implementation("com.google.code.gson:gson:2.11.0")
    implementation(libs.appcompat)
    implementation(libs.material)
    implementation(libs.activity)
    implementation(libs.constraintlayout)
    testImplementation(libs.junit)
    androidTestImplementation(libs.ext.junit)
    androidTestImplementation(libs.espresso.core)
}