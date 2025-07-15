
import com.foodi.buildlogic.convention.implementation
import com.foodi.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidWorkerConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            pluginManager.apply {
                apply("diatomicsoft.android.library")
                apply("diatomicsoft.android.hilt")
                apply("org.jetbrains.kotlin.plugin.serialization")
            }

            dependencies {
                implementation(libs.findLibrary("worker").get())
            }
        }
    }
}
