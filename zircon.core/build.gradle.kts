plugins {
    kotlinMpp
}

kotlin {
    jvm {
        jvmTarget(JavaVersion.VERSION_1_8)
        withJava()
    }

    dependencies {
        with (Libs) {
            commonMainApi(kotlinStdLibCommon)
            commonMainApi(kotlinxCoroutinesCommon)
            commonMainApi(cobaltEvents)
            commonMainApi(cobaltDatabinding)
            commonMainApi(cobaltDatatypes)
            commonMainApi(cobaltLogging)
        }

        with (LibsTest) {
            commonTestImplementation(kotlinTestCommon)
            commonTestImplementation(kotlinTestAnnotationsCommon)
        }

        with (Libs) {
            jvmMainApi(kotlinStdLibJdk8)
            jvmMainApi(kotlinReflect)
            jvmMainApi(kotlinxCoroutines)
            jvmMainApi(kotlinxCollectionsImmutable)
            jvmMainApi(caffeine)
            jvmMainApi(snakeYaml)
            jvmMainApi(slf4jApi)
        }

        with (LibsTest) {
            jvmTestApi(junit)
            jvmTestApi(mockitoAll)
            jvmTestApi(assertJCore)
            jvmTestApi(logbackClassic)
            jvmTestApi(logbackCore)
        }
    }
}