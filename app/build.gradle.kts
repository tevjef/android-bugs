import org.jetbrains.kotlin.gradle.plugin.KotlinPlatformType

plugins {
    alias(libs.plugins.kotlin)
    alias(libs.plugins.androidApplication)
    alias(libs.plugins.composeCompiler)
    id("com.google.devtools.ksp") version "2.3.0"
    id("org.jetbrains.compose") version "1.9.3"
    id("dev.zacsweers.metro") version "0.8.0"
    id("org.jetbrains.kotlin.plugin.parcelize")
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
    androidTarget()

    compilerOptions {
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
                // Uncomment to use working version that's defined transitively
                implementation("org.jetbrains.compose.runtime:runtime-saveable:1.10.0-rc02")
                // implementation("androidx.compose.runtime:runtime-saveable:1.10.0")

                // Compose
                implementation(compose.animation)
                implementation(compose.foundation)
                implementation(compose.material3)
                implementation(compose.runtime)
                implementation(compose.ui)
                implementation(project.dependencies.platform(libs.composeBom))

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

dependencies {
    add("kspAndroid", "com.slack.circuit:circuit-codegen:0.31.0")
    add("kspCommonMainMetadata", "com.slack.circuit:circuit-codegen:0.31.0")
}

