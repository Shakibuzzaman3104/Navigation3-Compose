plugins {
    alias(libs.plugins.diatomicsoft.android.application)
    alias(libs.plugins.diatomicsoft.android.application.compose)
    alias(libs.plugins.diatomicsoft.android.hilt)
    alias(libs.plugins.kotlin.compose) // Required for kotlin version 2.0.0 and above
    alias(libs.plugins.jetbrains.kotlin.serialization)
}

android {
    namespace = "com.diatomicsoft.navigation3"
}

dependencies {

    implementation(project(":core:database"))
    implementation(project(":core:network"))
    
    implementation(libs.androidx.core.ktx)
    implementation(libs.androidx.lifecycle.runtime.ktx)
    implementation(libs.androidx.activity.compose)
    implementation(platform(libs.androidx.compose.bom))
    implementation(libs.androidx.ui)
    implementation(libs.androidx.ui.graphics)
    implementation(libs.androidx.ui.tooling.preview)
    implementation(libs.androidx.material3)
    implementation(libs.androidx.material3.windowsizeclass)
    implementation(libs.androidx.adaptive.layout)
    implementation(libs.androidx.navigation3.runtime)
    implementation(libs.androidx.navigation3.ui)

    implementation(libs.kotlinx.serialization.core)
    implementation(libs.kotlinx.serialization.json)
    implementation(libs.androidx.navigation.compose)
    implementation(libs.androidx.navigation.runtime)
    implementation(libs.androidx.navigation.ui)
    implementation(libs.androidx.hilt.navigation.compose)
    implementation(libs.androidx.lifecycle.viewmodel.navigation)
    implementation(libs.androidx.material.icons.extended)

    implementation(libs.hilt.android)
    implementation(libs.androidx.navigation.runtime.android)
    ksp(libs.hilt.compiler)

    implementation(libs.moshi) // Or the latest version
    implementation(libs.moshi.kotlin) // For Kotlin extensions
    ksp(libs.moshi.kotlin.codegen)
    
    // Network dependencies needed for DI
    implementation(libs.retrofit)
    implementation(libs.retrofit.converter.moshi)
    implementation(libs.okhttp3.logging.interceptor)



    implementation(libs.timber)
    implementation(libs.coil.compose)

    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
    androidTestImplementation(platform(libs.androidx.compose.bom))
    androidTestImplementation(libs.androidx.ui.test.junit4)
    debugImplementation(libs.androidx.ui.tooling)
    debugImplementation(libs.androidx.ui.test.manifest)

    implementation(platform(libs.firebase.bom))

}