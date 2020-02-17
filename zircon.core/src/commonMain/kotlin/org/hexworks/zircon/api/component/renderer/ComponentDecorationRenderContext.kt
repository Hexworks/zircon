package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.graphics.StyleSet


data class ComponentDecorationRenderContext(val component: Component) : RenderContext {

    val currentStyle: StyleSet
        get() = component.componentStyleSet.fetchStyleFor(component.componentState)
}
