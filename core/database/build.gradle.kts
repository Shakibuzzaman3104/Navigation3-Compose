plugins {
    alias(libs.plugins.diatomicsoft.android.library)
    alias(libs.plugins.diatomicsoft.android.hilt)
    alias(libs.plugins.ksp)
}

android {
    namespace = "com.diatomicsoft.core.database"
}

dependencies {
    implementation(libs.androidx.room.runtime)
    ksp(libs.androidx.room.compiler)
    implementation(libs.androidx.room.ktx)
    
    implementation(libs.moshi)
    implementation(libs.moshi.kotlin)
    
    testImplementation(libs.junit)
    androidTestImplementation(libs.androidx.junit)
    androidTestImplementation(libs.androidx.espresso.core)
}