import org.openapitools.generator.gradle.plugin.tasks.GenerateTask

val inputDirPath = "${rootProject.projectDir.path}/src/main/resources/openapi"
val outputDirPath = "${rootProject.buildDir.path}/${project.name.split("-").joinToString("/")}"

project.buildDir = file("$outputDirPath/build")

plugins {
    id("java")
    id("java-library")
    id("maven-publish")
    id("org.openapi.generator") version "5.3.0"
    id("org.springframework.boot")
    id("io.spring.dependency-management")
}

sourceSets {
    main {
        java {
            srcDir("$outputDirPath/src/main/java")
        }
    }
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("io.swagger:swagger-annotations:1.5.17")
    implementation("io.springfox:springfox-swagger2:2.9.2")
    implementation("org.openapitools:jackson-databind-nullable:0.1.0")
    implementation("javax.validation:validation-api")
}

tasks {
    openApiGenerate {
        generatorName.set("spring")
        inputSpec.set("$inputDirPath/api.yml")
        outputDir.set(outputDirPath)
        apiPackage.set("com.vroong.urban.web.api.current")
        modelPackage.set("com.vroong.urban.web.api.model")
        modelNameSuffix.set("DTO")
        apiFilesConstrainedTo.set(listOf(""))
        modelFilesConstrainedTo.set(listOf(""))
        supportingFilesConstrainedTo.set(listOf("ApiUtil.java"))
        configOptions.set(mapOf(
            "delegatePattern" to "true",
            "useTags" to "true",
            "serviceInterface" to "true",
            "enumPropertyNaming" to "original",
            "serviceImplementation" to "true",
            "unhandledException" to "true",
            "serializableModel" to "true"
        ))
    }
    //getByName("openApiGenerate").dependsOn(openApiGenerateV1)

    compileJava {
        dependsOn(openApiGenerate)
    }

    jar {
        enabled = true
    }

    bootJar {
        enabled = false
    }

    bootRun {
        enabled = false
    }

    publish {
        dependsOn("normalJar")
        dependsOn("sourceJar")
    }
}
