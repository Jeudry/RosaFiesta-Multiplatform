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

include("features:chat:domain")
include("features:chat:infra")
include("features:chat:service")
include("features:chat:api")

include("features:notification:domain")
include("features:notification:infra")
include("features:notification:service")
include("features:notification:api")

include("features:user:domain")
include("features:user:infra")
include("features:user:service")
include("features:user:api")

include("features:products:domain")
include("features:products:infra")
include("features:products:service")
include("features:products:api")

include("core:domain")
include("core:infra")
include("core:service")
include("core:api")