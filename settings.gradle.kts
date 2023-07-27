pluginManagement { repositories {  mavenLocal(); mavenCentral(); google(); gradlePluginPortal()  }  }

rootProject.name = "zircon"

include(":zircon.core")
include(":zircon.examples")
