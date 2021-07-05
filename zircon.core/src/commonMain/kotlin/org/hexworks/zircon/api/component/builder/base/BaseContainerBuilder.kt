package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.builder.ComponentBuilder
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size

/**
 * This class can be used as a base class for creating [ComponentBuilder]s.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
abstract class BaseContainerBuilder<T : Container, U : ComponentBuilder<T, U>>(
        initialRenderer: ComponentRenderer<out T>
) : BaseComponentBuilder<T, U>(initialRenderer) {

    var childrenToAdd: List<Component> = listOf()

    abstract fun calculateContentSize(): Size

    private val calculatedSize: Size
        get() = calculateContentSize() + decorations.occupiedSize

    fun withChildren(vararg children: Component): U {
        this.childrenToAdd = children.toList()
        return this as U
    }

    fun withAddedChildren(vararg children: Component): U {
        this.childrenToAdd = childrenToAdd + children.toList()
        return this as U
    }

    override fun withSize(size: Size): U {
        preferredSize = size
        return this as U
    }

    override fun createMetadata() = ComponentMetadata(
            relativePosition = position,
            size = if (hasNoExplicitSize()) calculatedSize else size,
            tileset = tileset,
            componentStyleSet = componentStyleSet,
            theme = colorTheme,
            updateOnAttach = updateOnAttach,
            name = name
    )

    private fun hasNoExplicitSize() = preferredContentSize.isUnknown && preferredSize.isUnknown

}
