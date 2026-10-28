package org.hexworks.zircon.api.dsl

import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer

public operator fun ComponentDecorationRenderer.plus(
    other: ComponentDecorationRenderer
): List<ComponentDecorationRenderer> {
    return listOf(this, other)
}

public operator fun <T : Component> T.plus(other: T): List<T> {
    return listOf(this, other)
}


public operator fun Container.plus(other: Component): AttachedComponent {
    return this.addComponent(other)
}
