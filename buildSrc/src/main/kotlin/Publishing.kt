@file:Suppress("LocalVariableName")

import org.gradle.api.Project
import org.gradle.api.publish.PublishingExtension
import org.gradle.api.publish.maven.MavenPublication
import org.gradle.api.tasks.bundling.Jar
import org.gradle.kotlin.dsl.getValue
import org.gradle.kotlin.dsl.provideDelegate
import org.gradle.kotlin.dsl.registering
import org.gradle.kotlin.dsl.withType

fun PublishingExtension.publishWith(
        project: Project,
        module: String,
        desc: String
) {

    with(project) {

        val emptyJavadocJar by tasks.registering(Jar::class) {
            archiveClassifier.set("javadoc")
        }

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

        publications.withType<MavenPublication>().all {

            pom {

                name.set(module)
                description.set(desc)
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

            artifact(emptyJavadocJar.get())
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
}
