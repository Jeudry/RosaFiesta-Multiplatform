plugins {
    id("rosafiesta.service")
    id("rosafiesta.spring-boot-service")
    kotlin("plugin.spring")
    kotlin("plugin.jpa")
}

group = "com.rosafiesta.products"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("products-service")
}

dependencies {
    api(projects.features.products.domain)
    api(projects.features.products.infra)
    implementation(projects.core.service)

    implementation(libs.spring.boot.starter.security)
    implementation(libs.jwt.api)
    runtimeOnly(libs.jwt.impl)
    runtimeOnly(libs.jwt.jackson)
}