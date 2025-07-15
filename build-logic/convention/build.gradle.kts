import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
    `kotlin-dsl`
}

java {
    sourceCompatibility = JavaVersion.VERSION_17
    targetCompatibility = JavaVersion.VERSION_17
}

tasks.withType<KotlinCompile>().configureEach {
    compilerOptions {
        jvmTarget.set(JvmTarget.JVM_17)
    }
}
dependencies {
    compileOnly(libs.android.gradlePlugin)
    compileOnly(libs.android.tools.common)
    compileOnly(libs.kotlin.gradlePlugin)
    compileOnly(libs.ksp.gradlePlugin)
}

tasks {
    validatePlugins {
        enableStricterValidation = true
        failOnWarning = true
    }
}

gradlePlugin {
    plugins {
        register("androidApplicationCompose") {
            id = "diatomicsoft.android.application.compose"
            implementationClass = "AndroidApplicationComposeConventionPlugin"
        }
        register("androidApplication") {
            id = "diatomicsoft.android.application"
            implementationClass = "AndroidApplicationConventionPlugin"
        }
        register("androidLibraryCompose") {
            id = "diatomicsoft.android.library.compose"
            implementationClass = "AndroidLibraryComposeConventionPlugin"
        }
        register("androidLibrary") {
            id = "diatomicsoft.android.library"
            implementationClass = "AndroidLibraryConventionPlugin"
        }
        register("androidFeature") {
            id = "diatomicsoft.android.feature"
            implementationClass = "AndroidFeatureConventionPlugin"
        }
        register("androidHilt") {
            id = "diatomicsoft.android.hilt"
            implementationClass = "AndroidHiltConventionPlugin"
        }

        register("androidKTor") {
            id = "diatomicsoft.ktor"
            implementationClass = "AndroidNetworkConventionPlugin"
        }

        register("androidNavigation") {
            id = "diatomicsoft.navigation"
            implementationClass = "AndroidNavigationConventionPlugin"
        }

        register("androidRoom") {
            id = "diatomicsoft.database"
            implementationClass = "AndroidDatabaseConventionPlugin"
        }

        register("androidMoshi") {
            id = "diatomicsoft.moshi"
            implementationClass = "AndroidMoshiConventionPlugin"
        }

        register("androidWorker") {
            id = "diatomicsoft.worker"
            implementationClass = "AndroidWorkerConventionPlugin"
        }
    }
}
