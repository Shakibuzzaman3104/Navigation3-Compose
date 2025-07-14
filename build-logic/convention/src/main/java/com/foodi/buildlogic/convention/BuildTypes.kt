package com.foodi.buildlogic.convention

import com.android.build.api.dsl.ApplicationExtension
import com.android.build.api.dsl.BuildType
import com.android.build.api.dsl.CommonExtension
import com.android.build.api.dsl.LibraryExtension
import org.gradle.api.Project
import org.gradle.kotlin.dsl.configure
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import java.util.Properties

internal fun Project.configureBuildTypes(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    extensionType: ExtensionType,
) {
    commonExtension.run {
        buildFeatures {
            buildConfig = true
        }

        val flavorPropertiesFile =
            when {
                project.gradle.startParameter.taskNames.any {
                    it.contains("Prod", ignoreCase = true)
                } -> rootProject.file("flavorProd.properties")

                project.gradle.startParameter.taskNames.any {
                    it.contains("Staging", ignoreCase = true)
                } -> rootProject.file("flavorStage.properties")

                else -> rootProject.file("flavorDev.properties")
            }

        val flavorProperties = Properties()

        if (flavorPropertiesFile.exists()) {
            flavorPropertiesFile.inputStream().use { stream ->
                flavorProperties.load(stream)
            }
        }

        val apiUrl: String? = flavorProperties["API_URL"] as? String
        val mapApiKey: String? = flavorProperties["MAP_API_KEY"] as? String
        val mqttUrl: String? = flavorProperties["MQTT_URL"] as? String
        val cdnImageUrl: String? = flavorProperties["CDN_IMAGE_URL"] as? String
        val helpCenterUrl: String? = flavorProperties["HELP_CENTER_URL"] as? String
        val privacyPolicyUrl: String? = flavorProperties["PRIVACY_POLICY_URL"] as? String
        val termsAndConditionUrl: String? = flavorProperties["TERMS_CONDITION_URL"] as? String
        val tutorialUrl: String? = flavorProperties["TUTORIAL_URL"] as? String
        val sxsrfRequestKey: String? = flavorProperties["SXSRF_REQUEST_KEY"] as? String
        val sxsrfResponseKey: String? = flavorProperties["SXSRF_RESPONSE_KEY"] as? String
        val headerOrigin: String? = flavorProperties["HEADER_ORIGIN"] as? String
        val chatUrl: String? = flavorProperties["API_URL_CHAT"] as? String
        val appSignature: String? = flavorProperties["APP_SIGNATURE"] as? String

        when (extensionType) {
            ExtensionType.APPLICATION -> {
                extensions.configure<ApplicationExtension> {
                    
                    // Configure defaultConfig for APPLICATION
                    defaultConfig {
                        targetSdk = 36
                        versionCode = 1
                        versionName = "1.0.0"
                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                        
                        mapApiKey?.let {
                            manifestPlaceholders["MAP_API_KEY"] = it
                        }

                        val df: DateFormat = SimpleDateFormat("ddMMMyyyy_HH_mm_a", Locale.ENGLISH)

                        val timeStampOfBuild = df.format(Date())

                        val variant = if (isBuildVariant("Stage")) "Stage" else ""

                        testInstrumentationRunner = "androidx.test.runner.AndroidJUnitRunner"
                        setProperty(
                            "archivesBaseName",
                            "Navigation3$variant $versionName V_$versionCode $timeStampOfBuild",
                        )
                    }
                    
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiUrl, mapApiKey, mqttUrl, cdnImageUrl, 
                                helpCenterUrl, privacyPolicyUrl, termsAndConditionUrl, tutorialUrl,
                                sxsrfRequestKey, sxsrfResponseKey, headerOrigin, chatUrl)
                        }
                        release {
                            configureReleaseBuildType(
                                commonExtension,
                                apiUrl, mapApiKey, mqttUrl, cdnImageUrl, helpCenterUrl,
                                privacyPolicyUrl, termsAndConditionUrl, tutorialUrl,
                                sxsrfRequestKey, sxsrfResponseKey, headerOrigin, chatUrl
                            )
                        }
                    }
                    
                    flavorDimensions.add("version")

                    productFlavors {
                        create("Prod") {
                            dimension = "version"
                            applicationId = "com.diatomicsoft"
                            buildConfigField("String", "appSignature", "\"$appSignature\"")
                        }

                        create("Staging") {
                            dimension = "version"
                            applicationId = "com.diatomicsoft.stage"
                            resValue("string", "app_name", "Navigation3 Stage")
                            buildConfigField("String", "appSignature", "\"$appSignature\"")
                        }
                    }
                }
            }

            ExtensionType.LIBRARY -> {
                extensions.configure<LibraryExtension> {
                    buildTypes {
                        debug {
                            configureDebugBuildType(apiUrl, mapApiKey, mqttUrl, cdnImageUrl,
                                helpCenterUrl, privacyPolicyUrl, termsAndConditionUrl, tutorialUrl,
                                sxsrfRequestKey, sxsrfResponseKey, headerOrigin, chatUrl)
                        }
                        release {
                            configureReleaseBuildType(commonExtension, apiUrl, mapApiKey, mqttUrl, 
                                cdnImageUrl, helpCenterUrl, privacyPolicyUrl, termsAndConditionUrl,
                                tutorialUrl, sxsrfRequestKey, sxsrfResponseKey, headerOrigin, chatUrl)
                        }
                    }
                }
            }
        }
    }
}

internal fun Project.isBuildVariant(variant: String): Boolean =
    gradle.startParameter.taskNames.any { it.contains(variant, ignoreCase = true) }

private fun BuildType.configureDebugBuildType(
    apiUrl: String?,
    mapApiKey: String?,
    mqttUrl: String?,
    cdnImageUrl: String?,
    helpCenterUrl: String?,
    privacyPolicyUrl: String?,
    termsAndConditionUrl: String?,
    tutorialUrl: String?,
    sxsrfRequestKey: String?,
    sxsrfResponseKey: String?,
    headerOrigin: String?,
    chatUrl: String?
) {
    buildConfigField("String", "API_URL", "\"$apiUrl\"")
    buildConfigField("String", "MAP_API_KEY", "\"$mapApiKey\"")
    buildConfigField("String", "MQTT_URL", "\"$mqttUrl\"")
    buildConfigField("String", "CDN_IMAGE_URL", "\"$cdnImageUrl\"")
    buildConfigField("String", "HELP_CENTER_URL", "\"$helpCenterUrl\"")
    buildConfigField("String", "PRIVACY_POLICY_URL", "\"$privacyPolicyUrl\"")
    buildConfigField("String", "TERMS_CONDITION_URL", "\"$termsAndConditionUrl\"")
    buildConfigField("String", "TUTORIAL_URL", "\"$tutorialUrl\"")
    buildConfigField("String", "SXSRF_REQUEST_KEY", "\"$sxsrfRequestKey\"")
    buildConfigField("String", "SXSRF_RESPONSE_KEY", "\"$sxsrfResponseKey\"")
    buildConfigField("String", "HEADER_ORIGIN", "\"$headerOrigin\"")
    buildConfigField("String", "API_URL_CHAT", "\"$chatUrl\"")

    mapApiKey?.let {
        manifestPlaceholders["MAP_API_KEY"] = it
    }
}

private fun BuildType.configureReleaseBuildType(
    commonExtension: CommonExtension<*, *, *, *, *, *>,
    apiUrl: String?,
    mapApiKey: String?,
    mqttUrl: String?,
    cdnImageUrl: String?,
    helpCenterUrl: String?,
    privacyPolicyUrl: String?,
    termsAndConditionUrl: String?,
    tutorialUrl: String?,
    sxsrfRequestKey: String?,
    sxsrfResponseKey: String?,
    headerOrigin: String?,
    chatUrl: String?
) {
    buildConfigField("String", "API_URL", "\"$apiUrl\"")
    buildConfigField("String", "MAP_API_KEY", "\"$mapApiKey\"")
    buildConfigField("String", "MQTT_URL", "\"$mqttUrl\"")
    buildConfigField("String", "CDN_IMAGE_URL", "\"$cdnImageUrl\"")
    buildConfigField("String", "HELP_CENTER_URL", "\"$helpCenterUrl\"")
    buildConfigField("String", "PRIVACY_POLICY_URL", "\"$privacyPolicyUrl\"")
    buildConfigField("String", "TERMS_CONDITION_URL", "\"$termsAndConditionUrl\"")
    buildConfigField("String", "TUTORIAL_URL", "\"$tutorialUrl\"")
    buildConfigField("String", "SXSRF_REQUEST_KEY", "\"$sxsrfRequestKey\"")
    buildConfigField("String", "SXSRF_RESPONSE_KEY", "\"$sxsrfResponseKey\"")
    buildConfigField("String", "HEADER_ORIGIN", "\"$headerOrigin\"")
    buildConfigField("String", "API_URL_CHAT", "\"$chatUrl\"")

    isMinifyEnabled = true
    proguardFiles(
        commonExtension.getDefaultProguardFile("proguard-android-optimize.txt"),
        "proguard-rules.pro",
    )

    mapApiKey?.let {
        manifestPlaceholders["MAP_API_KEY"] = it
    }
}
