package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.dsl.buildChildFor
import org.hexworks.zircon.internal.component.impl.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class LogAreaBuilder : BaseComponentBuilder<LogArea>(
    initialRenderer = DefaultLogAreaRenderer()
) {

    var logRowHistorySize: Int = 100

    fun withLogRowHistorySize(numberOfRows: Int) = also {
        logRowHistorySize = numberOfRows
    }

    override fun build(): LogArea {
        return DefaultLogArea(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
        ).attachListeners()
    }
}

/**
 * Creates a new [LogArea] using the component builder DSL and returns it.
 */
fun buildLogArea(init: LogAreaBuilder.() -> Unit): LogArea =
    LogAreaBuilder().apply(init).build()

/**
 * Creates a new [LogArea] using the component builder DSL, adds it to the
 * receiver [BaseContainerBuilder] it and returns the [LogArea].
 */
fun <T : BaseContainerBuilder<*>> T.logArea(
    init: LogAreaBuilder.() -> Unit
): LogArea = buildChildFor(this, LogAreaBuilder(), init)

