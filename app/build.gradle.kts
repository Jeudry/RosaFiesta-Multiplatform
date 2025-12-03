import org.springframework.boot.gradle.tasks.bundling.BootJar

plugins {
    id("rosafiesta.spring-boot-app")
}

group = "com.rosafiesta"
version = "0.0.1-SNAPSHOT"
description = "RosaFiesta API backend"

tasks {
  named<BootJar>("bootJar") {
    from(project(":notification").projectDir.resolve("src/main/resources")){
      into("")
    }
    from(project(":user").projectDir.resolve("src/main/resources")) {
      into("")
    }
  }
}

dependencies {
    implementation(projects.common)
    implementation(projects.user)
    implementation(projects.chat)
    implementation(projects.notification)

    implementation(libs.spring.boot.starter.data.redis)
    implementation(libs.spring.boot.starter.amqp)
    implementation(libs.spring.boot.starter.data.jpa)
    implementation(libs.spring.boot.starter.security)
    implementation(libs.jackson.datatype.jsr310)

    implementation(libs.kotlin.reflect)
    
    // Liquibase for database migrations
    implementation("org.liquibase:liquibase-core")

    runtimeOnly(libs.postgresql)
}