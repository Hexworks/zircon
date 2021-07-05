package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultPanel
import org.hexworks.zircon.internal.component.renderer.DefaultPanelRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class PanelBuilder : BaseContainerBuilder<Panel, PanelBuilder>(DefaultPanelRenderer()) {

    override fun build(): Panel {
        return DefaultPanel(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialTitle = title,
        ).apply {
            addComponents(*childrenToAdd.toTypedArray())
        }
    }

    override fun createCopy() = newBuilder()
            .withProps(props.copy())
            .withChildren(*childrenToAdd.toTypedArray())

    override fun calculateContentSize(): Size {
        // best effort
        val result = childrenToAdd.map { it.size }.fold(Size.zero(), Size::plus)
        return if (result < Size.one()) Size.one() else result
    }

    companion object {

        @JvmStatic
        fun newBuilder() = PanelBuilder()
    }

}
