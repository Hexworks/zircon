import org.gradle.kotlin.dsl.kotlin
import org.gradle.plugin.use.PluginDependenciesSpec
import org.gradle.plugin.use.PluginDependencySpec

inline val PluginDependenciesSpec.kotlinMpp: PluginDependencySpec get() = kotlin("multiplatform")
inline val PluginDependenciesSpec.kotlinJvm: PluginDependencySpec get() = kotlin("jvm")