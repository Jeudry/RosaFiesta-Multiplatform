plugins {
    id("java-library")
    id("rosafiesta.spring-boot-service")
    kotlin("plugin.spring")
}

group = "com.rosafiesta.chat"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("chat-api")
}

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    implementation(projects.core.domain)
    implementation(projects.core.api)
    implementation(projects.core.service)
    implementation(projects.chat.domain)
    implementation(projects.chat.infra)
    implementation(projects.chat.service)
    
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.websocket)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.jackson.datatype.jsr310)
    
    testImplementation(kotlin("test"))
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    jvmToolchain(21)
}