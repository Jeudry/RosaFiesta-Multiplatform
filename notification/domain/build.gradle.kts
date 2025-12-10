plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta.notification"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("notification-domain")
}

dependencies {
    implementation(projects.core.domain)
    
    testImplementation(kotlin("test"))
}