plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("notification-service")
}

dependencies {
    api(projects.features.notification.domain)
    api(projects.features.notification.infra)
    implementation(projects.core.domain)

    testImplementation(kotlin("test"))
}