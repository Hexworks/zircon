plugins {
    kotlin("jvm")
}

base.archivesBaseName = "zircon.jvm.examples"

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    dependencies {
        with(Projects) {
            api(zirconCore)
            api(zirconJvmSwing)
            api(zirconJvmLibgdx)
        }

        with (Libs) {
            api(gdx)
            api(gdxBox2D)
            api(gdxBackendLwjgl)
            api(gdxPlatform)
            api(gdxBox2DPlatform)
        }
    }
}
