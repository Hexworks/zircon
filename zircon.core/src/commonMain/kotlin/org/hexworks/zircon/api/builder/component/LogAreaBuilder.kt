package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.LogArea
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.internal.dsl.ZirconDsl
import org.hexworks.zircon.internal.component.impl.DefaultLogArea
import org.hexworks.zircon.internal.component.renderer.DefaultLogAreaRenderer
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class LogAreaBuilder(
    private var logRowHistorySize: Int = 100
) : BaseComponentBuilder<LogArea, LogAreaBuilder>(DefaultLogAreaRenderer()) {

    fun withLogRowHistorySize(numberOfRows: Int) = also {
        logRowHistorySize = numberOfRows
    }

    override fun build(): LogArea {
        return DefaultLogArea(
            componentMetadata = createMetadata(),
            renderingStrategy = createRenderingStrategy(),
        )
    }

    override fun createCopy() = newBuilder().withProps(props.copy())
        .withLogRowHistorySize(logRowHistorySize)

    companion object {

        @JvmStatic
        fun newBuilder() = LogAreaBuilder()
    }
}
