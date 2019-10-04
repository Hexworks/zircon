import org.gradle.kotlin.dsl.DependencyHandlerScope
import org.gradle.kotlin.dsl.project

object Projects {
    inline val DependencyHandlerScope.zirconCore get() = project(":zircon.core")
    inline val DependencyHandlerScope.zirconJvmSwing get() = project(":zircon.jvm.swing")
    inline val DependencyHandlerScope.zirconJvmLibgdx get() = project(":zircon.jvm.libgdx")
}