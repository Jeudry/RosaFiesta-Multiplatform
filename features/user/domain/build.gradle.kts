plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta.user"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("user-domain")
}

dependencies {
    implementation(projects.core.domain)
    
    testImplementation(kotlin("test"))
}