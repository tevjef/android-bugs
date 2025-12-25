import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType
import org.jetbrains.kotlin.gradle.plugin.mpp.apple.XCFramework
import java.util.Locale

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    alias(libs.plugins.composeMultiplatform)
    alias(libs.plugins.ksp)
    alias(libs.plugins.metro)
    alias(libs.plugins.kotlinParcelize)
}

android {
    namespace = "com.circuit.reproducer"
    compileSdk = 36

    defaultConfig {
        minSdk = 24
        targetSdk = 35
        applicationId = "com.circuit.reproducer"
        versionCode = 1
        versionName = "1.0"
    }

    compileOptions {
        sourceCompatibility = JavaVersion.VERSION_11
        targetCompatibility = JavaVersion.VERSION_11
    }
}

kotlin {
    val xcframeworkName = "Shared"
    val xcf = XCFramework(xcframeworkName)

    androidTarget()

    listOf(
        iosArm64(),
        iosSimulatorArm64(),
    ).forEach { iosTarget ->
        iosTarget.binaries.framework {
            baseName = xcframeworkName
            binaryOption("bundleId", "com.circuit.reproducer.${xcframeworkName}")
            xcf.add(this)
            isStatic = true
        }
    }

    sourceSets {
        commonMain {
            dependencies {
                // Circuit
                implementation(libs.circuitBackstack)
                implementation(libs.circuitFoundation)
                implementation(libs.circuitRuntime)
                implementation(libs.circuitRuntimePresenter)
                implementation(libs.circuitRuntimeUi)
                implementation(libs.circuitAnnotations)
                implementation(libs.circuitGestureNavigation)

                // Compose
                implementation(libs.jbComposeAnimation)
                implementation(libs.jbComposeRuntime)
                implementation(libs.jbComposeUiToolingPreview)
                implementation(libs.jbComposeFoundation)
                implementation(libs.jbComposeMaterial)
                implementation(libs.jbComposeMaterial3)
                implementation(libs.jbComposeUi)

                // Coroutines
                implementation(libs.kotlinCoroutinesCore)
            }
        }

        androidMain {
            dependencies {
                implementation(libs.composeActivity)
            }
        }
    }

    targets.configureEach {
        if (platformType == KotlinPlatformType.androidJvm) {
            compilations.configureEach {
                compileTaskProvider.configure {
                    compilerOptions {
                        freeCompilerArgs.addAll(
                            "-P",
                            "plugin:org.jetbrains.kotlin.parcelize:additionalAnnotation=com.circuit.reproducer.Parcelize",
                        )
                    }
                }
            }
        }
    }
}

ksp {
    arg("circuit.codegen.mode", "metro")
}

fun String.capitalizeUS() = replaceFirstChar {
    if (it.isLowerCase()) it.titlecase(Locale.US) else it.toString()
}

val kspTargets = kotlin.targets.names.map { it.capitalizeUS() }

dependencies {
    for (target in kspTargets) {
        val targetConfigSuffix = if (target == "Metadata") "CommonMainMetadata" else target
        println("####### ksp${targetConfigSuffix}")
        add("ksp${targetConfigSuffix}", libs.circuitCodegen)
    }
}

