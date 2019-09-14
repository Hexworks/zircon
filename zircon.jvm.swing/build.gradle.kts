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
            compile(zirconCore)
        }

        with (Libs) {
            compile(cobaltEvents)
            compile(cobaltDatatypes)
            compile(cobaltDatabinding)
            compile(cobaltLoggingJvm)
            compile(filters)
        }

        with (LibsTest) {
            testCompile(junit)
            testCompile(mockitoAll)
            testCompile(assertJCore)
        }
    }
}

val sourcesJar by tasks.registering(Jar::class) {
    classifier = "sources"
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