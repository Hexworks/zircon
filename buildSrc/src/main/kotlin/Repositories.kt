import org.gradle.api.artifacts.dsl.RepositoryHandler
import org.gradle.kotlin.dsl.maven

fun RepositoryHandler.kotlinx() = maven("https://dl.bintray.com/kotlin/kotlinx")
fun RepositoryHandler.jitpack() = maven("https://jitpack.io")