import korlibs.korge.gradle.*

plugins {
	id("com.soywiz.korge")
}

korge {
	id = "com.soywiz.samples.animations"
	targetJs()
	targetJvm()
}

dependencies {
	add("commonMainApi", project(":zircon.core"))
}
