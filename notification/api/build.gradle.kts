plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.notification.domain)
    api(projects.notification.service)
    implementation(projects.core.domain)
    implementation(projects.core.api)

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)

    testImplementation(kotlin("test"))
}