plugins {
    kotlinJvm
}

base.archivesBaseName = "zircon.jvm.libgdx"

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    dependencies {
        with(Projects) {
            compile(zirconCore)
        }

        with (Libs) {
            compile(gdx)
            compile(gdxFreetype)
            compile(gdxFreetypePlatform)
            compile(gdxBox2D)
            compile(gdxBackendLwjgl)
            compile(gdxPlatform)
            compile(gdxBox2DPlatform)
            compile(logbackClassic)
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