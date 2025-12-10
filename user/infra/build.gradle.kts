plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta.user"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.user.domain)
    implementation(projects.core)

    implementation(libs.spring.boot.starter.web)
    api(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.security)
    runtimeOnly(libs.postgresql)

    testImplementation(kotlin("test"))
}