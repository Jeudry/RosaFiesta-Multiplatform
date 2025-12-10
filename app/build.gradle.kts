import org.springframework.boot.gradle.tasks.bundling.BootJar
plugins {
    id("rosafiesta.spring-boot-app")
}
group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"
description = "RosaFiesta API backend"
springBoot {
    mainClass.set("com.rosafiesta.api.RosaFiestaApiApplicationKt")
}
tasks {
  named<BootJar>("bootJar") {
    from(project(":features:notification:infra").projectDir.resolve("src/main/resources")){
      into("")
    }
    // User and notification modules have resources in infra submodule
  }
}
dependencies {
    // Core modules
    implementation(projects.core.domain)
    implementation(projects.core.infra)
    implementation(projects.core.service)
    implementation(projects.core.api)
    // User modules
    implementation(projects.features.user.domain)
    implementation(projects.features.user.infra)
    implementation(projects.features.user.service)
    implementation(projects.features.user.api)
    // Chat modules
    implementation(projects.features.chat.domain)
    implementation(projects.features.chat.infra)
    implementation(projects.features.chat.service)
    implementation(projects.features.chat.api)
    // Notification modules
    implementation(projects.features.notification.domain)
    implementation(projects.features.notification.infra)
    implementation(projects.features.notification.service)
    implementation(projects.features.notification.api)
    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jackson.datatype.jsr310)
    implementation(libs.kotlin.reflect)
    implementation(libs.flyway.core)
    implementation(libs.flyway.postgresql)
    implementation(libs.liquibase.core)
    runtimeOnly(libs.postgresql)
}