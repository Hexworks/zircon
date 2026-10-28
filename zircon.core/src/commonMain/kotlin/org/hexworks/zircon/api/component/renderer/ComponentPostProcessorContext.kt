package org.hexworks.zircon.api.component.renderer

import org.hexworks.zircon.api.component.Component

public class ComponentPostProcessorContext<T : Component>(public val component: T) : RenderContext