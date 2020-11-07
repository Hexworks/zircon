@file:Suppress("UnstableApiUsage")

import org.jetbrains.dokka.gradle.DokkaTask
import java.net.URL

plugins {
    kotlin("jvm")
    id("org.jetbrains.dokka")
    id("maven-publish")
    id("signing")
}

base.archivesBaseName = "zircon.jvm.libgdx"

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    dependencies {
        with(Projects) {
            api(zirconCore)
        }

        with(Libs) {
            api(cobaltCore)
            api(kotlinxCollectionsImmutable)

            api(gdx)
            api(gdxFreetype)
            api(gdxFreetypePlatform)
            api(gdxBox2D)
            api(gdxBackendLwjgl)
            api(gdxPlatform)
            api(gdxBox2DPlatform)
            api(logbackClassic)
        }

        with(TestLibs) {
            testImplementation(junit)
            testImplementation(mockitoAll)
            testImplementation(assertjCore)
        }
    }
}

tasks.withType<DokkaTask>().configureEach {
    dokkaSourceSets {
        configureEach {
            includeNonPublic.set(false)
            skipDeprecated.set(false)
            reportUndocumented.set(true)
            skipEmptyPackages.set(true)
            includes.from("module.md", "packages.md")
            sourceLink {
                localDirectory.set(file("src/main/kotlin"))
                remoteUrl.set(URL("https://github.com/Hexworks/zircon/tree/master/zircon.jvm.libgdx/src/main/kotlin"))
                remoteLineSuffix.set("#L")
            }
            jdkVersion.set(8)
            noStdlibLink.set(false)
            noJdkLink.set(false)
            noAndroidSdkLink.set(false)
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    with(archiveClassifier) {
        convention("sources")
        set("sources")
    }
    from(sourceSets.main.get().allSource)
}

val emptyJavadocJar by tasks.registering(Jar::class) {
    archiveClassifier.set("javadoc")
}

signing {
    isRequired = false
    sign(publishing.publications)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
            artifact(emptyJavadocJar.get())

            val POM_URL: String by project
            val POM_SCM_URL: String by project
            val POM_SCM_CONNECTION: String by project
            val POM_SCM_DEV_CONNECTION: String by project
            val POM_LICENCE_NAME: String by project
            val POM_LICENCE_URL: String by project
            val POM_LICENCE_DIST: String by project
            val POM_DEVELOPER_ID: String by project
            val POM_DEVELOPER_NAME: String by project
            val POM_DEVELOPER_EMAIL: String by project
            val POM_DEVELOPER_ORGANIZATION: String by project
            val POM_DEVELOPER_ORGANIZATION_URL: String by project

            pom {

                name.set("zircon.jvm.swing")
                description.set("LibGDX Implementation for Zircon")
                url.set(POM_URL)

                scm {
                    url.set(POM_SCM_URL)
                    connection.set(POM_SCM_CONNECTION)
                    developerConnection.set(POM_SCM_DEV_CONNECTION)
                }

                licenses {
                    license {
                        name.set(POM_LICENCE_NAME)
                        url.set(POM_LICENCE_URL)
                        distribution.set(POM_LICENCE_DIST)
                    }
                }

                developers {
                    developer {
                        id.set(POM_DEVELOPER_ID)
                        name.set(POM_DEVELOPER_NAME)
                        email.set(POM_DEVELOPER_EMAIL)
                        organization.set(POM_DEVELOPER_ORGANIZATION)
                        organizationUrl.set(POM_DEVELOPER_ORGANIZATION_URL)
                    }
                }
            }
        }
    }

    repositories {

        val sonatypeUsername = System.getenv("SONATYPE_USERNAME") ?: ""
        val sonatypePassword = System.getenv("SONATYPE_PASSWORD") ?: ""

        maven {
            url = uri("https://oss.sonatype.org/service/local/staging/deploy/maven2/")
            credentials {
                username = if (sonatypeUsername.isBlank()) "" else sonatypeUsername
                password = if (sonatypePassword.isBlank()) "" else sonatypePassword
            }
        }
    }
}

artifacts {
    archives(emptyJavadocJar)
    archives(sourcesJar)
}
