plugins {
    id("rosafiesta.api")
}
group = "com.rosafiesta.user"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("user-api")
}
dependencies {
    api(projects.features.user.domain)
    api(projects.features.user.service)
    implementation(projects.core.service)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.jwt.api)
}