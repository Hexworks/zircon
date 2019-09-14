package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet

class ComponentRenderContext<out T : Component>(val component: T) : RenderContext {

    val componentStyle: ComponentStyleSet
        get() = component.componentStyleSet
}
