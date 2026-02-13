package org.hexworks.zircon.api.component.renderer.extensions

import org.hexworks.zircon.api.component.data.ComponentState.DISABLED
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderContext
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.NON_INTERACTIVE
import org.hexworks.zircon.api.graphics.StyleSet

/**
 * Returns the corresponding [StyleSet] for the given [RenderingMode].
 * For [RenderingMode.INTERACTIVE] the [currentStyle] will be returned,
 * for [RenderingMode.NON_INTERACTIVE] the [defaultStyle].
 */
fun ComponentDecorationRenderContext.fetchStyleFor(renderingMode: RenderingMode): StyleSet {
    if (component.isDisabled) {
        return currentComponentStyle.fetchStyleFor(DISABLED)
    }
    return when (renderingMode) {
        INTERACTIVE -> currentStyle
        NON_INTERACTIVE -> defaultStyle
    }
}
