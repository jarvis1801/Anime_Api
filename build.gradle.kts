val ktorVersion: String by project
val kotlinVersion: String by project
val logbackVersion: String by project
val twelvemonkeysVersion: String by project

plugins {
    application
    kotlin("jvm") version "1.6.10"
    kotlin("plugin.serialization") version "1.6.10"
}

group = ""
version = "0.0.1"
application {
    mainClass.set("io.ktor.server.netty.EngineMain")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

repositories {
    mavenCentral()
    maven { url = uri("https://maven.pkg.jetbrains.space/public/p/ktor/eap") }
}

dependencies {
    // basic
    implementation("io.ktor:ktor-server-netty:$ktorVersion")
    implementation("io.ktor:ktor-client-core:$ktorVersion")
    implementation("io.ktor:ktor-client-cio:$ktorVersion")
    implementation("io.ktor:ktor-html-builder:$ktorVersion")
    implementation("ch.qos.logback:logback-classic:$logbackVersion")
    testImplementation("io.ktor:ktor-server-tests:$ktorVersion")
    implementation("io.ktor:ktor-auth:$ktorVersion")
    implementation("io.ktor:ktor-auth-jwt:$ktorVersion")

    // mongo
    implementation("org.litote.kmongo:kmongo:4.5.0")

    // serialization
    implementation("com.google.code.gson:gson:2.9.0")
    implementation("org.jetbrains.kotlinx:kotlinx-serialization-json:1.3.2")

    // skrapeit
    implementation("it.skrape:skrapeit:1.2.1")

    // image
    implementation("com.twelvemonkeys.common:common-lang:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.common:common-io:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.common:common-image:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.imageio:imageio-core:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.imageio:imageio-metadata:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.imageio:imageio-bmp:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.imageio:imageio-jpeg:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.imageio:imageio-tiff:$twelvemonkeysVersion")
    implementation("com.twelvemonkeys.imageio:imageio-webp:$twelvemonkeysVersion")
}