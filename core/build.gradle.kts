import org.jetbrains.kotlin.gradle.ExperimentalWasmDsl
import org.jetbrains.kotlin.gradle.dsl.JvmTarget
import com.codingfeline.buildkonfig.compiler.FieldSpec.Type

val appEnvironment = providers
    .gradleProperty("buildConfig.flavor")
    .orElse("uat")
    .get()
    .lowercase()

val environmentConfigs = mapOf(
    "uat" to ("https://jsonplaceholder.typicode.com" to false),
    "staging" to ("https://jsonplaceholder.typicode.com" to false),
    "prod" to ("https://jsonplaceholder.typicode.com" to true),
)

val selectedConfig = environmentConfigs[appEnvironment]
    ?: error("Unknown app.environment: $appEnvironment")

plugins {
    alias(libs.plugins.kotlinMultiplatform)
    alias(libs.plugins.androidMultiplatformLibrary)
    alias(libs.plugins.buildkonfig)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.ksp)
    alias(libs.plugins.room3)
}

kotlin {
    iosArm64()
    iosSimulatorArm64()
    
    jvm()
    
    js {
        browser()
    }
    
    @OptIn(ExperimentalWasmDsl::class)
    wasmJs {
        browser()
    }
    
    android {
       namespace = "com.android.my_first_kmp_app.core"
       compileSdk = libs.versions.android.compileSdk.get().toInt()
       minSdk = libs.versions.android.minSdk.get().toInt()

       lint {
           abortOnError = true
           checkDependencies = true
           warningsAsErrors = false
           explainIssues = true
       }
    
       compilerOptions {
           jvmTarget = JvmTarget.JVM_11
       }
       androidResources {
           enable = true
       }
       withHostTest {
           isIncludeAndroidResources = true
       }
    }
    
    sourceSets {
        commonMain.dependencies {
            // Koin dependency injection
            implementation(libs.koin.core)
            implementation(libs.koin.compose)
            implementation(libs.koin.compose.viewmodel)

            // Ktor Client
            implementation(libs.ktor.client.core)
            implementation(libs.ktor.client.engine.defaults)
            implementation(libs.ktor.client.content.negotiation)
            implementation(libs.ktor.serialization.kotlinx.json)
            implementation(libs.ktor.client.logging)
            implementation(libs.ktor.client.auth)

            // Room database
            implementation(libs.room.runtime)
        }

        commonTest.dependencies {
            implementation(libs.kotlin.test)
            implementation(libs.kotlinx.coroutinesTest)
        }

        androidMain.dependencies {
            implementation(libs.ktor.client.android)
            implementation(libs.sqlite.bundled)
        }

        iosMain.dependencies {
            implementation(libs.ktor.client.darwin)
            implementation(libs.sqlite.bundled)
        }

        jvmMain.dependencies {
            implementation(libs.ktor.client.cio)
            implementation(libs.sqlite.bundled)
        }

        jsMain.dependencies {
            implementation(libs.ktor.client.js)
        }

        wasmJsMain.dependencies {
            implementation(libs.ktor.client.js)
        }
    }
}

dependencies {
    add("kspAndroid", libs.room.compiler)
    add("kspJvm", libs.room.compiler)
    add("kspIosArm64", libs.room.compiler)
    add("kspIosSimulatorArm64", libs.room.compiler)
    add("kspJs", libs.room.compiler)
    add("kspWasmJs", libs.room.compiler)
}

room3 {
    schemaDirectory("$projectDir/schemas")
}

buildkonfig {
    packageName = "com.android.my_first_kmp_app.app.core"

    defaultConfigs {
        buildConfigField(Type.STRING, "ENVIRONMENT", appEnvironment)
        buildConfigField(Type.STRING, "BASE_URL", selectedConfig.first)
        buildConfigField(
            Type.BOOLEAN,
            "IS_PRODUCTION",
            selectedConfig.second.toString(),
        )
    }
}
