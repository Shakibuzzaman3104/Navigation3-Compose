
import com.foodi.buildlogic.convention.implementation
import com.foodi.buildlogic.convention.libs
import com.foodi.buildlogic.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidDatabaseConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }

            dependencies {
                implementation(libs.findLibrary("room-runtime").get())
                "ksp"(libs.findLibrary("room-ksp").get())
                implementation(libs.findLibrary("room-ktx").get())
                testImplementation(libs.findLibrary("room-testing").get())

                "api"(libs.findLibrary("datastore-preferences").get())
            }
        }
    }
}
