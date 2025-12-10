import org.gradle.kotlin.dsl.internal.relocated.kotlin.metadata.internal.metadata.deserialization.VersionRequirementTable.Companion.create
import org.gradle.kotlin.dsl.maven

dependencyResolutionManagement {
    versionCatalogs {
        create("libs") {
            from(files("gradle/libs.version.toml"))
        }
    }
}

pluginManagement {
    includeBuild("build-logic")
    repositories {
        gradlePluginPortal()
        maven { url = uri("https://repo.spring.io/milestone") }
        maven { url = uri("https://repo.spring.io/snapshot") }
    }
}

enableFeaturePreview("TYPESAFE_PROJECT_ACCESSORS")

rootProject.name = "rosafiesta-api"

include("app")

include("chat:domain")
include("chat:infra")
include("chat:service")
include("chat:api")

include("notification:domain")
include("notification:infra")
include("notification:service")
include("notification:api")

include("user:domain")
include("user:infra")
include("user:service")
include("user:api")

include("core:domain")
include("core:infra")
include("core:service")
include("core:api")