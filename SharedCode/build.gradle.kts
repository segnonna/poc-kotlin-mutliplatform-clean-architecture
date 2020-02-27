import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinNativeTarget

plugins {
    kotlin("multiplatform")
}

kotlin {
    //select iOS target platform depending on the Xcode environment variables
    val iOSTarget: (String, KotlinNativeTarget.() -> Unit) -> KotlinNativeTarget =
        if (System.getenv("SDK_NAME")?.startsWith("iphoneos") == true)
            ::iosArm64
        else
            ::iosX64

    iOSTarget("ios") {
        binaries {
            framework {
                baseName = "SharedCode"
            }
        }
    }

    jvm("android")

    val serialization_version = "0.11.0"
    val ktor_version = "1.3.0"
    val coroutines_version = "1.3.3"
    sourceSets["commonMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib-common")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-common:$serialization_version")
        // HTTP
        implementation ("io.ktor:ktor-client-core:${ktor_version}")
        implementation ("io.ktor:ktor-client-json:${ktor_version}")
        implementation ("io.ktor:ktor-client-serialization:${ktor_version}")

        // Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-common:${ktor_version}")
    }

    sourceSets["androidMain"].dependencies {
        implementation("org.jetbrains.kotlin:kotlin-stdlib")
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime:${serialization_version}")

        // HTTP
        implementation ("io.ktor:ktor-client-android:${ktor_version}")
        implementation ("io.ktor:ktor-client-json-jvm:${ktor_version}")
        implementation ("io.ktor:ktor-client-serialization-jvm:${ktor_version}")
        implementation ("io.ktor:ktor-client-okhttp:${ktor_version}")
        implementation ("com.squareup.okhttp3:logging-interceptor:3.14.1")

        // Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-android:${coroutines_version}")
    }

    sourceSets["iosMain"].dependencies {
        implementation ("org.jetbrains.kotlinx:kotlinx-serialization-runtime-native:${serialization_version}")

        // HTTP
        implementation ("io.ktor:ktor-client-ios:${ktor_version}")
        implementation ("io.ktor:ktor-client-json-native:${ktor_version}")
        implementation ("io.ktor:ktor-client-serialization-iosx64:${ktor_version}")

        // Coroutines
        implementation ("org.jetbrains.kotlinx:kotlinx-coroutines-core-native:${coroutines_version}")
    }
}

val packForXcode by tasks.creating(Sync::class) {
    val targetDir = File(buildDir, "xcode-frameworks")

    /// selecting the right configuration for the iOS
    /// framework depending on the environment
    /// variables set by Xcode build
    val mode = System.getenv("CONFIGURATION") ?: "DEBUG"
    val framework = kotlin.targets
        .getByName<KotlinNativeTarget>("ios")
        .binaries.getFramework(mode)
    inputs.property("mode", mode)
    dependsOn(framework.linkTask)

    from({ framework.outputDirectory })
    into(targetDir)

    /// generate a helpful ./gradlew wrapper with embedded Java path
    doLast {
        val gradlew = File(targetDir, "gradlew")
        gradlew.writeText("#!/bin/bash\n"
                + "export 'JAVA_HOME=${System.getProperty("java.home")}'\n"
                + "cd '${rootProject.rootDir}'\n"
                + "./gradlew \$@\n")
        gradlew.setExecutable(true)
    }
}

tasks.getByName("build").dependsOn(packForXcode)