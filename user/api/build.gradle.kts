plugins {
    id("rosafiesta.kotlin-common")
    id("java-library")
}
group = "com.rosafiesta.user"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("user-api")
}
dependencies {
    api(projects.user.domain)
    api(projects.user.service)
    implementation(projects.core.domain)
    implementation(projects.core.api)
    implementation(projects.core.service)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.jwt.api)
    testImplementation(kotlin("test"))
}