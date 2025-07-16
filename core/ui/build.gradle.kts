plugins {
    alias(libs.plugins.diatomicsoft.android.library)
    alias(libs.plugins.diatomicsoft.android.library.compose)
    alias(libs.plugins.kotlin.android)
    alias(libs.plugins.kotlin.compose)
}

android {
    namespace = "com.diatomicsoft.core.ui"
}

dependencies {
    implementation(libs.androidx.material.icons.extended)
}