plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"

dependencies {
    implementation(projects.common)
    
    testImplementation(kotlin("test"))
}