plugins {
    kotlinMpp
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

            commonMainApi(cobaltEvents)
            commonMainApi(cobaltDatabinding)
            commonMainApi(cobaltDatatypes)
            commonMainApi(cobaltLogging)
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
            jvmTestApi(junit)
            jvmTestApi(mockitoAll)
            jvmTestApi(mockitoKotlin)
            jvmTestApi(assertJCore)
            jvmTestApi(logbackClassic)
            jvmTestApi(logbackCore)
        }
    }
}