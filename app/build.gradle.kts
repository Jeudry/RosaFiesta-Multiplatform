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
    from(project(":notification").projectDir.resolve("src/main/resources")){
      into("")
    }
    // User modules don't have resources (modularized into submodules)
  }
}
dependencies {
    implementation(projects.common)
    // User modules
    implementation(projects.userDomain)
    implementation(projects.userInfra)
    implementation(projects.userService)
    implementation(projects.userApi)
    implementation(projects.chat)
    implementation(projects.notification)
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
