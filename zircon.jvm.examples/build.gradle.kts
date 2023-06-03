//import korlibs.korge.gradle.*
//
//apply<KorgeGradlePlugin>()
//
//korge {
//    id = "org.hexworks.zircon.examples"
//    targetJvm()
//    targetJs()
//}

dependencies {
    project(":zircon.core")
    project(":zircon.jvm.swing")
    //add("commonMainApi", "org.openjdk.jol:jol-core:0.13")
}
