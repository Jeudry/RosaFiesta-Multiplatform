plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta.user"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("user-service")
}

dependencies {
    api(projects.user.domain)
    api(projects.user.infra)
    implementation(projects.core.domain)
    implementation(projects.core.service)
    implementation(projects.core.infra)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)

    testImplementation(kotlin("test"))
}