import com.foodi.buildlogic.convention.implementation
import com.foodi.buildlogic.convention.ksp
import com.foodi.buildlogic.convention.libs
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.dependencies

class AndroidMoshiConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.google.devtools.ksp")
            }
            dependencies {
                implementation(libs.findLibrary("moshi").get())
                implementation(libs.findLibrary("moshi-kotlin").get())
                ksp(libs.findLibrary("moshi-kotlin-codegen").get())
                implementation(libs.findLibrary("moshi-adapters").get())
            }
        }
    }
}
