plugins {
    application
    kotlin("jvm") version "1.9.22"
}

repositories {
    mavenCentral()
    mavenLocal()
}

dependencies {
    implementation("io.ktor:ktor-server-core:2.3.9")
    implementation("io.ktor:ktor-server-cio:2.3.9")
    implementation("io.ktor:ktor-server-call-logging:2.3.9")
    implementation("io.ktor:ktor-server-html-builder:2.3.9")
    implementation("org.webjars:webjars-locator-lite:0.0.4")
    runtimeOnly("org.webjars:bootstrap:5.3.2")
    runtimeOnly("org.slf4j:slf4j-simple:2.0.12")
}

application {
    mainClass = "WebAppKt"
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=true")
}

kotlin {
    jvmToolchain(8)
}

tasks.replace("assemble").dependsOn("installDist")

tasks.create("stage").dependsOn("installDist")
