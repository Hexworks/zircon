package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component

class ComponentRenderContext<T : Component>(val component: T) : RenderContext {

    fun componentStyle() = component.componentStyleSet()
}
