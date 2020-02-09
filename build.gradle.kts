allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        kotlinx()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}