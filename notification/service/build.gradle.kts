plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.notification.domain)
    api(projects.notification.infra)
    implementation(projects.common)

    testImplementation(kotlin("test"))
}