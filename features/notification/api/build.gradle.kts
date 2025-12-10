plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("notification-api")
}

dependencies {
    api(projects.features.notification.domain)
    api(projects.features.notification.service)
    implementation(projects.core.domain)
    implementation(projects.core.api)

    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.validation)

    testImplementation(kotlin("test"))
}