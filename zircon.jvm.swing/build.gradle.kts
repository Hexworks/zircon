plugins {
    kotlinJvm
}

base.archivesBaseName = "zircon.jvm.swing"

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    dependencies {

        with(Projects) {
            api(zirconCore)
        }

        with (Libs) {
            api(cobaltCore)
            api(kotlinxCollectionsImmutable)
            api(filters)
        }

        with (TestLibs) {
            testImplementation(junit)
            testImplementation(mockitoAll)
            testImplementation(assertJCore)
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    with (archiveClassifier) {
        convention("sources")
        set("sources")
    }
    from(sourceSets.main.get().allSource)
}

publishing {
    publications {
        register("mavenJava", MavenPublication::class) {
            from(components["java"])
            artifact(sourcesJar.get())
        }
    }
}

artifacts {
    archives(sourcesJar)
}