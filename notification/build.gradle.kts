plugins {
    id("java-library")
    id("rosafiesta.spring-boot-service")
    kotlin("plugin.spring")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"

repositories {
    mavenCentral()
    maven { url = uri("https://repo.spring.io/milestone") }
    maven { url = uri("https://repo.spring.io/snapshot") }
}

dependencies {
    testImplementation(kotlin("test"))
    implementation(libs.firebase.admin.sdk)
  
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.mail)
  implementation(libs.spring.boot.starter.web)
  implementation(libs.spring.boot.starter.thymeleaf)
  implementation(libs.spring.boot.starter.data.jpa)
  implementation(libs.spring.boot.starter.validation)
  
  runtimeOnly(libs.postgresql)
  
  implementation(projects.common)
}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(21)
}