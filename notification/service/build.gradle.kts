plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

dependencies {
    api(projects.notification.domain)
    api(projects.notification.infra)
    implementation(projects.core.domain)

    testImplementation(kotlin("test"))
}