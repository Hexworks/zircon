package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent

class DefaultVBox internal constructor(
    componentMetadata: ComponentMetadata,
    initialTitle: String,
    private val spacing: Int,
    renderingStrategy: ComponentRenderingStrategy<VBox>
) : VBox, DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
),
    TitleOverride by TitleOverride.create(initialTitle) {

    private var filledUntil = Position.create(0, 0)
    private var availableSpace = contentSize.toRect()

    override val remainingSpace: Int
        get() = availableSpace.height

    override fun addComponent(component: Component): InternalAttachedComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        checkAvailableSpace(component)
        val finalSpacing = if (children.isEmpty()) 0 else spacing
        val finalHeight = component.height + finalSpacing
        component.moveDownBy(filledUntil.y + finalSpacing)
        filledUntil = filledUntil.withRelativeY(finalHeight)
        availableSpace = availableSpace.withRelativeHeight(-finalHeight)
        return VBoxAttachmentDecorator(super<DefaultContainer>.addComponent(component))
    }

    private inner class VBoxAttachmentDecorator(
        val attachedComponent: InternalAttachedComponent
    ) : InternalAttachedComponent by attachedComponent {
        override fun detach(): Component {
            reorganizeComponents(attachedComponent.component)
            return attachedComponent.detach()
        }
    }

    private fun reorganizeComponents(component: Component) {
        val height = component.height
        val delta = height + if (children.isEmpty()) 0 else spacing
        val y = component.position.y
        children.filter {
            it.position.y > y
        }.forEach {
            it.moveUpBy(delta)
        }
        filledUntil = filledUntil.withRelativeY(-delta)
        availableSpace = availableSpace.withRelativeHeight(delta)
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toContainerStyle()

    private fun checkAvailableSpace(component: Component) =
        require(availableSpace.containsBoundable(component.rect)) {
            "There is not enough space ${availableSpace.size} left in $this to add $component as a child."
        }

}
