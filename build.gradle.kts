import org.jetbrains.compose.desktop.application.dsl.TargetFormat

plugins {
    kotlin("jvm") version "1.9.10"
    id("org.jetbrains.compose") version "1.5.3"
}

group = "com.github.freitzzz"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
    maven("https://maven.pkg.jetbrains.space/public/p/compose/dev")
    maven("https://jitpack.io")

    google()
}

dependencies {
    // Note, if you develop a library, you should use compose.desktop.common.
    // compose.desktop.currentOs should be used in launcher-sourceSet
    // (in a separate module for demo project and in testMain).
    // With compose.desktop.common you will also lose @Preview functionality
    implementation(compose.desktop.currentOs)

    implementation("com.github.kotlin-artisans:lumberkodee:0.0.1")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.6.0")
    implementation("uk.co.caprica:vlcj:4.7.0")
}

compose.desktop {
    application {
        mainClass = "MainKt"

        nativeDistributions {
            targetFormats(TargetFormat.Dmg, TargetFormat.Msi, TargetFormat.Deb)
            packageName = "Simpsons Shorts"
            packageVersion = "1.0.0"
            description = "A desktop app to watch Simpsons shorts while your monolith app compiles. Made to learn Compose Multiplatform!"
            copyright = "freitzzz"

            windows {
                iconFile.set(project.file("src/main/resources/assets/icons/simpsons-shorts-icon.ico"))
            }
            linux {
                iconFile.set(project.file("src/main/resources/assets/icons/simpsons-shorts-icon.png"))
            }
        }
    }
}
