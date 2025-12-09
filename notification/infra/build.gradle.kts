plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.notification.domain)
    implementation(projects.common)

    implementation(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.thymeleaf)
    implementation(libs.firebase.admin.sdk)
    runtimeOnly(libs.postgresql)

    testImplementation(kotlin("test"))
}