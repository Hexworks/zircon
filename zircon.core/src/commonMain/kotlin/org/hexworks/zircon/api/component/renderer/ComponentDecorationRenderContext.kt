package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentState
import org.hexworks.zircon.api.component.data.ComponentState.DISABLED
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.graphics.StyleSet


data class ComponentDecorationRenderContext(
        val component: Component
) : RenderContext {

    /**
     * The current [StyleSet] which is used by the [component]. The style
     * depends on the [Component.componentState].
     */
    val currentStyle: StyleSet
        get() = currentComponentStyle.fetchStyleFor(component.componentState)

    /**
     * The [StyleSet] which is used for the [ComponentState.DEFAULT] state
     * for [component].
     */
    val defaultStyle: StyleSet
        get() = currentComponentStyle.fetchStyleFor(ComponentState.DEFAULT)

    private val currentComponentStyle: ComponentStyleSet
        get() = component.componentStyleSet

    /**
     * Returns the corresponding [StyleSet] for the given [RenderingMode].
     * For [RenderingMode.INTERACTIVE] the [currentStyle] will be returned,
     * for [RenderingMode.NON_INTERACTIVE] the [defaultStyle].
     */
    fun fetchStyleFor(renderingMode: RenderingMode): StyleSet {
        if(component.isDisabled) {
            return currentComponentStyle.fetchStyleFor(DISABLED)
        }
        return when (renderingMode) {
            INTERACTIVE -> currentStyle
            NON_INTERACTIVE -> defaultStyle
        }
    }
}
