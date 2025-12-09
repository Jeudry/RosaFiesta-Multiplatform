plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.userDomain)
    api(projects.userService)
    implementation(projects.common)

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.jwt.api)

    testImplementation(kotlin("test"))
}