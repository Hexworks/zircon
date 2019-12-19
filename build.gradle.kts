allprojects {
    repositories {
        mavenLocal() {
            metadataSources {
                gradleMetadata()
                mavenPom()
            }
        }
        mavenCentral() {
            metadataSources {
                gradleMetadata()
                mavenPom()
            }
        }
        jcenter() {
            metadataSources {
                gradleMetadata()
                mavenPom()
            }
        }
        kotlinx()
        jitpack()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}