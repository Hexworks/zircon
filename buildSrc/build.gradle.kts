plugins {
    `kotlin-dsl`
}

repositories {
    mavenLocal()
    mavenCentral()
    google()
    maven { url = uri("https://plugins.gradle.org/m2/") }
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.8.21")
    //implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.7.10")
    implementation("com.soywiz.korlibs.korge.plugins:korge-gradle-plugin:4.0.3")
}
