import com.soywiz.korge.gradle.*

apply<KorgeGradlePlugin>()

korge {
	id = "com.soywiz.samples.animations"
	targetJs()
	targetJvm()
}

dependencies {
	project(":zircon.core")
}
