plugins {
    kotlinJvm
}

base.archivesBaseName = "zircon.jvm.examples"

kotlin {
    target {
        jvmTarget(JavaVersion.VERSION_1_8)
    }

    dependencies {
        with(Projects) {
            implementation(zirconCore)
            implementation(zirconJvmSwing)
            implementation(zirconJvmLibgdx)
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