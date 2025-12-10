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
    duplicatesStrategy = DuplicatesStrategy.EXCLUDE
    from(project(":notification:infra").projectDir.resolve("src/main/resources")){
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
    implementation(projects.user.domain)
    implementation(projects.user.infra)
    implementation(projects.user.service)
    implementation(projects.user.api)
    // Chat modules
    implementation(projects.chat.domain)
    implementation(projects.chat.infra)
    implementation(projects.chat.service)
    implementation(projects.chat.api)
    // Notification modules
    implementation(projects.notification.domain)
    implementation(projects.notification.infra)
    implementation(projects.notification.service)
    implementation(projects.notification.api)
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
