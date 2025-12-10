plugins {
    id("rosafiesta.infra")
    id("rosafiesta.spring-boot-service")
    kotlin("plugin.spring")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("notification-infra")
}

dependencies {
    api(projects.features.notification.domain)

    implementation(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.mail)
    implementation(libs.spring.boot.starter.thymeleaf)
    implementation(libs.firebase.admin.sdk)
    runtimeOnly(libs.postgresql)
}