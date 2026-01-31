package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component

class ComponentPostProcessorContext<T : Component>(val component: T) : RenderContext