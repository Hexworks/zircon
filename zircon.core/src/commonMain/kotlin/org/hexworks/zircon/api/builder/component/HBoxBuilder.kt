package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.builder.base.BaseContainerBuilder
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.internal.component.impl.DefaultHBox
import org.hexworks.zircon.internal.component.renderer.DefaultHBoxRenderer
import org.hexworks.zircon.internal.dsl.ZirconDsl
import kotlin.math.max
import kotlin.jvm.JvmStatic

@Suppress("UNCHECKED_CAST")
@ZirconDsl
class HBoxBuilder : BaseContainerBuilder<HBox, HBoxBuilder>(DefaultHBoxRenderer()) {

    var spacing: Int = 0
        set(value) {
            require(value >= 0) {
                "Can't use a negative spacing"
            }
            field = value
        }

    fun withSpacing(spacing: Int) = also {
        this.spacing = spacing
    }

    override fun build(): HBox {
        return DefaultHBox(
                componentMetadata = createMetadata(),
                renderingStrategy = createRenderingStrategy(),
                initialTitle = title,
                spacing = spacing,
        ).apply {
            addComponents(*childrenToAdd.toTypedArray())
        }
    }

    override fun createCopy() = newBuilder()
            .withProps(props.copy())
            .withSpacing(spacing)
            .withChildren(*childrenToAdd.toTypedArray())

    @Suppress("DuplicatedCode")
    override fun calculateContentSize(): Size {
        if (childrenToAdd.isEmpty()) {
            return Size.one()
        }
        var width = 0
        var maxHeight = 0
        childrenToAdd
                .map { it.size }
                .forEach {
                    width += it.width
                    maxHeight = max(maxHeight, it.height)
                }
        if (spacing > 0) {
            width += childrenToAdd.size * spacing - 1
        }
        return Size.create(max(1, width), maxHeight)
    }

    companion object {

        @JvmStatic
        fun newBuilder() = HBoxBuilder()
    }
}
