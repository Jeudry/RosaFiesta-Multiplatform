plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.user.domain)
    api(projects.user.infra)
    implementation(projects.core)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    testImplementation(kotlin("test"))
}