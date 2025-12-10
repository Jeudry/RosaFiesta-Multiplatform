plugins {
    id("rosafiesta.infra")
    id("rosafiesta.spring-boot-service")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta.user"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("user-infra")
}

dependencies {
    api(projects.features.user.domain)

    implementation(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.security)
    runtimeOnly(libs.postgresql)
}