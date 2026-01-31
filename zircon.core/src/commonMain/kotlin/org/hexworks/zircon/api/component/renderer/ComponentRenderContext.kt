package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.graphics.StyleSet

class ComponentRenderContext<out T : Component>(val component: T) : RenderContext {

    val componentStyle: ComponentStyleSet
        get() = component.componentStyleSet

    val currentStyle: StyleSet
        get() = componentStyle.fetchStyleFor(component.componentState)

    val theme: ColorTheme
        get() = component.theme

    operator fun component1() = component
    operator fun component2() = componentStyle
    operator fun component3() = currentStyle
    operator fun component4() = theme


}
