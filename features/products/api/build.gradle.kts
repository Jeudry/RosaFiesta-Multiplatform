plugins {
    id("rosafiesta.api")
}
group = "com.rosafiesta.products"
version = "0.0.1-SNAPSHOT"

base {
    archivesName.set("products-api")
}
dependencies {
    api(projects.features.products.domain)
    api(projects.features.products.service)
    implementation(projects.core.service)
    implementation(libs.spring.boot.starter.web)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.spring.boot.starter.validation)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.jwt.api)
}