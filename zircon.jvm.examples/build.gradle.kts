plugins {
    kotlin("jvm")
    application
}

base.archivesBaseName = "zircon.jvm.examples"

application {
    mainClass.set("org.hexworks.zircon.benchmark.SwingBenchmark")
}

kotlin {
    dependencies {
        implementation(project(":zircon.core"))
        implementation(project(":zircon.jvm.swing"))
        implementation(project(":zircon.jvm.libgdx"))
        implementation("org.openjdk.jol:jol-core:0.13")

        with(Libraries) {
            implementation(gdx)
            implementation(gdxFreetype)
            implementation(gdxFreetypePlatform)
            implementation(gdxFreetypePlatformNatives)
            implementation(gdxPlatformNativesDesktop)
            implementation(gdxBox2D)
            implementation(gdxBox2DPlatform)
            implementation(gdxBackendLwjgl)
        }
    }
}
