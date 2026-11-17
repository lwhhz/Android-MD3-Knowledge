plugins {
    alias(libs.plugins.android.application)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.example.m3appbar"
    compileSdk = 37
    compileSdkMinor = 2

    defaultConfig {
        applicationId = "com.example.m3appbar"
        minSdk = 26
        targetSdk = 36
        versionCode = 1
        versionName = "0.1.0"
    }

    signingConfigs {
        getByName("debug") {
            // 沙箱不允许写 ~/.android，调试签名改到工程内（.gitignore 已排除）
            storeFile = rootProject.file(".debug/debug.keystore")
            storePassword = "android"
            keyAlias = "androiddebugkey"
            keyPassword = "android"
        }
    }

    buildTypes {
        release {
            isMinifyEnabled = false
        }
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_17
        targetCompatibility = JavaVersion.VERSION_17
    }

    buildFeatures {
        compose = true
    }

    sourceSets {
        getByName("main") {
            // 0.4：历史转写代码接入编译。先暴露真实编译错误，再逐项修。
            java.srcDirs(
                "src/main/java",
                "${rootDir}/components",
            )
            kotlin.srcDirs(
                "src/main/java",
                "${rootDir}/appbar",
                "${rootDir}/components",
            )
        }
    }
}

kotlin {
    compilerOptions {
        jvmTarget.set(org.jetbrains.kotlin.gradle.dsl.JvmTarget.JVM_17)
    }
}

dependencies {
    // 离线构建适配：本机缓存只有 navigationevent 1.1.2 的 -android 变体，
    // 而 activity-compose 1.13.0 默认解析到 1.1.1。上移一个补丁版本以命中缓存。
    // 联网环境下可去掉，改为按官方发布版本解析。
    constraints {
        implementation("androidx.navigationevent:navigationevent:1.1.2")
        implementation("androidx.navigationevent:navigationevent-compose:1.1.2")
    }

    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)

    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    // 图标依赖显式声明（KB 规则 V3）；BOM-alpha 未提供图标版本时用稳定版 1.7.8。
    implementation("androidx.compose.material:material-icons-core:1.7.8")

    debugImplementation(libs.androidx.ui.tooling)

    testImplementation(libs.junit)
}