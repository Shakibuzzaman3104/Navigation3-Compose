import com.android.build.gradle.LibraryExtension
import com.foodi.buildlogic.convention.ExtensionType
import com.foodi.buildlogic.convention.configureBuildTypes
import com.foodi.buildlogic.convention.configureKotlinAndroid
import com.foodi.buildlogic.convention.testImplementation
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import org.gradle.kotlin.dsl.dependencies
import org.gradle.kotlin.dsl.kotlin

class AndroidLibraryConventionPlugin : Plugin<Project> {
    override fun apply(target: Project) {
        with(target) {
            with(pluginManager) {
                apply("com.android.library")
                apply("org.jetbrains.kotlin.android")
            }

            extensions.configure<LibraryExtension> {
                configureKotlinAndroid(this)
                defaultConfig.targetSdk = 35

                configureBuildTypes(
                    commonExtension = this,
                    extensionType = ExtensionType.LIBRARY,
                )
            }

            dependencies {
                testImplementation(kotlin("test"))
            }
        }
    }
}
