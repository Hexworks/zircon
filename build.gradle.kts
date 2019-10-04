allprojects {
    repositories {
        mavenLocal()
        mavenCentral()
        jcenter()
        kotlinx()
        jitpack()
    }
}

subprojects {
    apply<MavenPublishPlugin>()
}