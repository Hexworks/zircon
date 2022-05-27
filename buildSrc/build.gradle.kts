plugins {
    `kotlin-dsl`
}

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.jetbrains.kotlin:kotlin-gradle-plugin:1.6.20")
}

kotlinDslPluginOptions {
    experimentalWarning.set(false)
}
