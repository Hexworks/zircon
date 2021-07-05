package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer

operator fun ComponentDecorationRenderer.plus(
        other: ComponentDecorationRenderer
): List<ComponentDecorationRenderer> {
    return listOf(this, other)
}

operator fun <T : Component> T.plus(other: T): List<T> {
    return listOf(this, other)
}


operator fun Container.plus(other: Component): AttachedComponent {
    return this.addComponent(other)
}
