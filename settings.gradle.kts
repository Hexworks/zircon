pluginManagement { repositories {  mavenLocal(); mavenCentral(); google(); gradlePluginPortal()  }  }

rootProject.name = "zircon"

include(":zircon.core")
include(":zircon.examples")

//include(":zircon.jvm.swing")
//include(":zircon.jvm.examples")
