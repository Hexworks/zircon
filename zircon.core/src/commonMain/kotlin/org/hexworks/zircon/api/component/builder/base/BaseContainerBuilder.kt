package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.data.Size

/**
 * This class can be used as a base class for creating component builders.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
abstract class BaseContainerBuilder<T : Container>(
    initialRenderer: ComponentRenderer<out T>
) : BaseComponentBuilder<T>(initialRenderer) {

    var childrenToAdd: List<Component> = listOf()

    abstract fun calculateContentSize(): Size

    private val calculatedSize: Size
        get() = calculateContentSize() + decorationRenderers.occupiedSize

    override fun createMetadata() = ComponentMetadata(
        relativePosition = position,
        size = if (hasNoExplicitSize()) calculatedSize else size,
        name = name,
        updateOnAttach = updateOnAttach,
        themeProperty = colorThemeProperty,
        componentStyleSetProperty = componentStyleSetProperty,
        tilesetProperty = tilesetProperty,
        hiddenProperty = hiddenProperty,
        disabledProperty = disabledProperty
    )

    private fun hasNoExplicitSize() = preferredContentSize.isUnknown && preferredSize.isUnknown

    /**
     * Creates a new [ChildrenBuilder] using the builder DSL and returns it.
     */
    fun BaseContainerBuilder<T>.children(init: ChildrenBuilder.() -> Unit): List<Component> {
        val builder = this
        return ChildrenBuilder().apply(init).build().apply {
            builder.childrenToAdd = this
        }
    }

}
