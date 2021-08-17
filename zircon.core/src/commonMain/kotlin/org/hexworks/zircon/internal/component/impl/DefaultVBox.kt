package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.events.api.DisposeSubscription
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.behavior.TitleOverride
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import kotlin.jvm.Synchronized

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

    @Synchronized
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

        whenConnectedToRoot { root ->
            root.eventBus.subscribeTo<ComponentRemoved>(root.eventScope) { (_, removedComponent) ->
                if (removedComponent == component) {
                    reorganizeComponents(component)
                    DisposeSubscription
                } else KeepSubscription
            }
        }

        return super<DefaultContainer>.addComponent(component)
    }

    private fun reorganizeComponents(component: Component) {
        val height = component.height
        val delta = height + if (children.isEmpty()) 0 else spacing
        val y = component.position.y
        children.filter {
            it.position.y >= y
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
