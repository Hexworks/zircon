package org.hexworks.zircon.api.component.builder.base

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.isSizeUnknown
import org.hexworks.zircon.api.component.theme.ComponentRole

/**
 * This class can be used as a base class for creating component builders.
 * If your component has text use [ComponentWithTextBuilder] instead.
 */
public abstract class BaseContainerBuilder<T : Container>(
    initialRenderer: ComponentRenderer<out T> = NoOpComponentRenderer(),
    initialRole: ComponentRole = ComponentRole.NONE,
) : BaseComponentBuilder<T>(initialRenderer, initialRole) {

    public var childrenToAdd: List<Component> = listOf()

    public abstract fun calculateContentSize(): Size

    private val calculatedSize: Size
        get() = calculateContentSize() + decorationRenderers.occupiedSize

    override fun createMetadata(): ComponentMetadata = applyPendingTheme().let { ComponentMetadata(
        position = position,
        size = if (hasNoExplicitSize()) calculatedSize else size,
        name = name,
        bindingAction = bindingAction,
        themeProperty = colorThemeProperty,
        componentStyleSetProperty = componentStyleSetProperty,
        tilesetProperty = tilesetProperty,
        hiddenProperty = hiddenProperty,
        disabledProperty = disabledProperty,
        activationShortcut = activationShortcut,
        kind = kind,
        role = role,
        variant = variant,
        part = part,
        orientation = orientation,
        componentThemeProperty = componentThemeProperty,
        pinnedAspects = props.pinnedAspects,
        componentThemeExplicit = props.componentThemeExplicit,
    ) }

    private fun applyPendingTheme() {
        applyThemeEntry(props.componentTheme)
    }

    private fun hasNoExplicitSize() = preferredContentSize.isSizeUnknown && preferredSize.isSizeUnknown

    /**
     * Creates a new [ChildrenBuilder] using the builder DSL and returns it.
     */
    public fun BaseContainerBuilder<T>.children(init: ChildrenBuilder.() -> Unit): List<Component> {
        val builder = this
        return ChildrenBuilder().apply(init).build().apply {
            builder.childrenToAdd = this
        }
    }

}
