plugins {
    id("java-library")
    kotlin("jvm")
}

group = "com.rosafiesta.chat"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("chat-domain")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(projects.core.domain)
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}