import org.gradle.internal.impldep.bsh.commands.dir
import org.jetbrains.kotlin.ir.backend.js.compile

plugins {
    id("com.android.application")
    id("org.jetbrains.kotlin.android")
}

android {
    namespace = "com.example.petwelfare"
    compileSdk = 34

    buildFeatures {
        viewBinding = true
    }

    defaultConfig {
        applicationId = "com.example.petwelfare"
        minSdk = 26
        targetSdk = 34
        versionCode = 1
        versionName = "1.0"

        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"

        ndk {
            // 设置支持的SO库架构（开发者可以根据需要，选择一个或多个平台的so）
            // 注意：'armeabi' 和 'armeabi-v7a' 在新的应用中已经不推荐使用，建议使用 'arm64-v8a'
            abiFilters.addAll(listOf("armeabi-v7a", "arm64-v8a", "x86", "x86_64"))
        }
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
    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {

    // 网络请求
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:okhttp:4.12.0")
    // livedata
    implementation("androidx.lifecycle:lifecycle-extensions:2.2.0")
    implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")

    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-core:1.6.4")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.6.4")

    implementation("androidx.activity:activity-ktx:1.7.0")
    // 内部储存
    implementation("androidx.datastore:datastore-preferences:1.0.0")
    // 圆形头像
    implementation("de.hdodenhof:circleimageview:3.1.0")

    implementation("com.google.android.material:material:1.9.0-beta01")

    // 下拉刷新
    implementation("androidx.swiperefreshlayout:swiperefreshlayout:1.1.0")

    // recyclerView
    implementation("androidx.recyclerview:recyclerview:1.3.2")

    // 获取定位
    implementation ("com.google.android.gms:play-services-location:21.0.1") // 使用最新的版本号
    implementation ("com.guolindev.permissionx:permissionx:1.6.4")

    // 高德地图
    implementation(fileTree(mapOf("dir" to "libs", "include" to listOf("*.jar"))))
    implementation ("com.amap.api:search:5.0.0") // 高德地图搜索 SDK
    //implementation ("com.amap.api:maps:3.3.0") // 高德地图 SDK
    implementation ("com.amap.api:location:3.3.0") // 高德定位 SDK

    // navigation
    implementation("androidx.navigation:navigation-fragment-ktx:2.5.3")
//                  androidx.navigation:navigation-fragment-ktx:版本号
    implementation("androidx.navigation:navigation-ui-ktx:2.5.3")
//                  androidx.navigation:navigation-ui-ktx:版本号

    // glide
    implementation("com.github.bumptech.glide:glide:4.16.0")
    implementation("androidx.activity:activity:1.8.0")
    annotationProcessor("com.github.bumptech.glide:compiler:4.16.0")

    // 基础
    implementation("androidx.core:core-ktx:1.9.0")
    implementation("androidx.appcompat:appcompat:1.6.1")
    implementation("com.google.android.material:material:1.8.0")
    implementation("androidx.constraintlayout:constraintlayout:2.1.4")
    implementation("androidx.legacy:legacy-support-v4:1.0.0")
    implementation("androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.1")
    testImplementation("junit:junit:4.13.2")
    androidTestImplementation("androidx.test.ext:junit:1.1.5")
    androidTestImplementation("androidx.test.espresso:espresso-core:3.5.1")
}