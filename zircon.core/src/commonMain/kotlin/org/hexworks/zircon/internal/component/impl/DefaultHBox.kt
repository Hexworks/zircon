package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import kotlin.jvm.Synchronized

open class DefaultHBox(
    componentMetadata: ComponentMetadata,
    initialTitle: String,
    private val spacing: Int,
    renderingStrategy: ComponentRenderingStrategy<HBox>
) : HBox, DefaultContainer(
    metadata = componentMetadata,
    renderer = renderingStrategy
),
    TitleOverride by TitleOverride.create(initialTitle) {

    private var filledUntil = Position.create(0, 0)
    private var availableSpace = contentSize.toRect()

    override val remainingSpace: Int
        get() = availableSpace.width

    @Synchronized
    override fun addComponent(component: Component): InternalAttachedComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        checkAvailableSpace(component)
        val finalSpacing = if (children.isEmpty()) 0 else spacing
        val finalWidth = component.width + finalSpacing
        component.moveRightBy(filledUntil.x + finalSpacing)
        filledUntil = filledUntil.withRelativeX(finalWidth)
        availableSpace = availableSpace.withRelativeWidth(-finalWidth)
        return HBoxAttachmentDecorator(super<DefaultContainer>.addComponent(component))
    }

    private inner class HBoxAttachmentDecorator(
        val attachedComponent: InternalAttachedComponent
    ) : InternalAttachedComponent by attachedComponent {
        override fun detach(): Component {
            reorganizeComponents(attachedComponent.component)
            return attachedComponent.detach()
        }
    }

    private fun reorganizeComponents(component: Component) {
        val width = component.width
        val delta = width + if (children.isEmpty()) 0 else spacing
        val x = component.position.x
        children.filter {
            it.position.x > x
        }.forEach {
            it.moveLeftBy(delta)
        }
        filledUntil = filledUntil.withRelativeX(-delta)
        availableSpace = availableSpace.withRelativeWidth(delta)
    }

    override fun convertColorTheme(colorTheme: ColorTheme) = colorTheme.toContainerStyle()

    private fun checkAvailableSpace(component: Component) =
        require(availableSpace.containsBoundable(component.rect)) {
            "There is not enough space ($availableSpace) left for the component: $component."
        }
}
