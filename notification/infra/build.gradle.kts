plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("notification-infra")
}

dependencies {
    api(projects.notification.domain)
    implementation(projects.core.domain)
    implementation(projects.core.infra)

    implementation(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.thymeleaf)
    implementation(libs.firebase.admin.sdk)
    runtimeOnly(libs.postgresql)

    testImplementation(kotlin("test"))
}