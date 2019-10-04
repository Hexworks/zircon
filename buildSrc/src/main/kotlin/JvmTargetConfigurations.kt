
import org.gradle.api.JavaVersion
import org.gradle.api.NamedDomainObjectContainer
import org.gradle.api.artifacts.Dependency
import org.gradle.api.artifacts.dsl.DependencyHandler
import org.jetbrains.kotlin.gradle.dsl.KotlinJvmOptions
import org.jetbrains.kotlin.gradle.dsl.KotlinProjectExtension
import org.jetbrains.kotlin.gradle.plugin.mpp.AbstractKotlinCompilationToRunnableFiles
import org.jetbrains.kotlin.gradle.plugin.mpp.KotlinWithJavaTarget
import org.jetbrains.kotlin.gradle.targets.jvm.KotlinJvmTarget

fun KotlinJvmTarget.jvmTarget(javaVersion: JavaVersion) =
        compilations.applyVersion(javaVersion)

fun KotlinWithJavaTarget<KotlinJvmOptions>.jvmTarget(javaVersion: JavaVersion) =
        compilations.applyVersion(javaVersion)

fun NamedDomainObjectContainer<out AbstractKotlinCompilationToRunnableFiles<KotlinJvmOptions>>.applyVersion(javaVersion: JavaVersion) {
    forEach { it.kotlinOptions.jvmTarget = "$javaVersion" }
}
fun DependencyHandler.jvmMainApi(dependencyNotation: Any): Dependency? =
        add("jvmMainApi", dependencyNotation)

fun DependencyHandler.jvmTestApi(dependencyNotation: Any): Dependency? =
        add("jvmTestApi", dependencyNotation)

fun KotlinProjectExtension.withZirconStyleSourceSets() {
    sourceSets.forEach {
        val name = it.kotlin.name.split(" ").firstOrNull() ?: return@forEach
        val type = when {
            name.endsWith("Main") -> "main"
            name.endsWith("Test") -> "test"
            else -> null
        } ?: return@forEach
        val prefix = name.substring(0 until (name.length - type.length))
        it.kotlin.srcDirs("$prefix/src/$type/kotlin")
        it.resources.srcDirs("$prefix/src/$type/resources")
    }
}