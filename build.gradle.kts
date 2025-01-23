
plugins {
    alias(libs.plugins.kotlin.jvm)
    alias(libs.plugins.ktor)
    alias(libs.plugins.kotlin.serialization)
    alias(libs.plugins.squareup.sqldelight)
}

group = "com.example"
version = "0.0.1"

sqldelight {
    databases {
        create("AppDatabase"){
            packageName.set("com.example")
        }
    }
}

application {
    mainClass.set("com.example.ApplicationKt")

    val isDevelopment: Boolean = project.ext.has("development")
    applicationDefaultJvmArgs = listOf("-Dio.ktor.development=$isDevelopment")
}

repositories {
    mavenCentral()
}

dependencies {
    implementation(libs.ktor.server.core)
    implementation(libs.ktor.server.netty)
    testImplementation(libs.ktor.server.test.host)
    implementation(libs.ktor.server.html.builder)
    implementation(libs.ktor.serialization.kotlinx.json)
    implementation(libs.ktor.server.content.negotiation)
    implementation(libs.ktor.server.cors)
    implementation(libs.ktor.client.cio)


    implementation(libs.sqldelight)

    implementation(libs.logback.classic)
    testImplementation(libs.kotlin.test.junit)
}
