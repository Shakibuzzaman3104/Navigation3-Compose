
import com.foodi.buildlogic.convention.implementation
import com.foodi.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidFeatureConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("diatomicsoft.android.library.compose")
                apply("diatomicsoft.android.library")
                apply("diatomicsoft.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
                apply("org.jetbrains.kotlin.plugin.compose")
            }

            dependencies {

                //implementation(project(":core:common_domain"))

                // Define common dependencies for feature modules
                implementation(libs.findLibrary("androidx-hilt-navigation-compose").get())
                implementation(libs.findLibrary("androidx-navigation-compose").get())
                implementation(libs.findLibrary("androidx-navigation3-runtime").get())
                implementation(libs.findLibrary("kotlinx-serialization-json").get())
                implementation(libs.findLibrary("coil-compose").get())
                implementation(libs.findLibrary("androidx-constraintlayout-compose").get())
                implementation(libs.findLibrary("lifecycle-runtime-compose").get())
                implementation(libs.findLibrary("androidx-material-icons-extended").get())
                implementation(project(":core:navigation"))
                implementation(project(":core:network"))
                implementation(project(":core:database"))
                implementation(project(":core:ui"))
            }
        }
    }
}
