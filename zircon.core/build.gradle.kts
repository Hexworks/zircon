plugins {
    kotlin("multiplatform")
    id("maven-publish")
    id("signing")
}

kotlin {

    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    dependencies {
        with(Libs) {
            commonMainApi(kotlinStdLibCommon)

            commonMainApi(kotlinxCoroutinesCommon)
            commonMainApi(kotlinxCollectionsImmutable)
            commonMainApi(kotlinReflect)

            commonMainApi(cobaltCore)
        }

        with(TestLibs) {
            commonTestImplementation(kotlinTestCommon)
            commonTestImplementation(kotlinTestAnnotationsCommon)
        }

        with(Libs) {
            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi(kotlinReflect)

            jvmMainApi(kotlinxCoroutines)

            jvmMainApi(caffeine)
            jvmMainApi(snakeYaml)
            jvmMainApi(slf4jApi)
        }

        with(TestLibs) {
            jvmTestApi(kotlinTestJunit)
            jvmTestApi(junit)
            jvmTestApi(mockitoAll)
            jvmTestApi(mockitoKotlin)
            jvmTestApi(assertJCore)
            jvmTestApi(logbackClassic)
            jvmTestApi(logbackCore)
        }
    }
}

publishing {
    publishWith(
            project = project,
            module = "zircon.core",
            desc = "Core component of Zircon."
    )
}

signing {
    isRequired = false
    sign(publishing.publications)
}