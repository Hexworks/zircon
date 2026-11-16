@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.theme.ComponentPalette
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.graphics.StyleSet

public class ComponentRenderContext<out T : Component>(public val component: T) : RenderContext {

    public val componentStyle: ComponentStyleSet
        get() = component.componentStyleSet

    public val currentStyle: StyleSet
        get() = componentStyle.fetchStyleFor(component.componentState)

    /**
     * The palette the component is drawn with, for colors the style set does not carry.
     */
    public val palette: ComponentPalette
        get() = component.componentPalette

    @Deprecated("Colors come from the component palette; read `palette` or `currentStyle`.")
    public val theme: ColorTheme
        get() = component.theme

    public operator fun component1(): T = component
    public operator fun component2(): ComponentStyleSet = componentStyle
    public operator fun component3(): StyleSet = currentStyle
    @Deprecated("Colors come from the component palette; read `palette` or `currentStyle`.")
    public operator fun component4(): ColorTheme = theme

    override fun toString(): String = "ComponentRenderContext(component=$component, componentStyle=$componentStyle, currentStyle=$currentStyle, palette=$palette)"
}
