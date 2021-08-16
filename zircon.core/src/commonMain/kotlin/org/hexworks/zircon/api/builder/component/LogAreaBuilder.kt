package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.component.impl.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class LogAreaBuilder private constructor() : BaseComponentBuilder<LogArea, LogAreaBuilder>(
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

    override fun createCopy() = newBuilder()
        .withProps(props.copy())
        .withLogRowHistorySize(logRowHistorySize)

    companion object {

        @JvmStatic
        fun newBuilder() = LogAreaBuilder()
    }
}
