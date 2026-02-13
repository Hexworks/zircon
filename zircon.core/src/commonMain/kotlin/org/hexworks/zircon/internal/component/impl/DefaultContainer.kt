package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.behavior.extensions.withPosition
import org.hexworks.zircon.api.component.AttachedComponent
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.component.extensions.flattenedTree
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved

open class DefaultContainer(
    metadata: ComponentMetadata,
    renderer: ComponentRenderingStrategy<out Component>
) : InternalContainer, DefaultComponent(
    metadata = metadata,
    renderer = renderer
) {

    final override val children: ObservableList<InternalComponent> =
        persistentListOf<InternalComponent>().toProperty()

    private val attachments = mutableListOf<AttachedComponent>()

    final override fun moveTo(position: Position): Boolean {
        val diff = position - this.position
        super.moveTo(position)
        //? You might think that this will fail as the container was already moved and might
        //? intersect with its children, but the bounds check only happens when the component
        //? is attached, so this will move the children back into "the safe zone"
        children.forEach {
            it.moveTo(it.position + diff)
        }
        return true
    }

    override fun addComponent(component: Component): InternalAttachedComponent {
        val (componentToAdd, newPosition) = checkIfCanAdd(component)
        val parent = this
        val attachment = DefaultAttachedComponent(componentToAdd, parent)
        component.moveTo(newPosition)
        children.add(componentToAdd)
        attachments.add(attachment)
        root?.let { root ->
            componentToAdd.flattenedTree.forEach { it.root = root }
            root.eventBus.publish(
                event = ComponentAdded(
                    parent = parent,
                    component = componentToAdd,
                    emitter = componentToAdd
                ),
                eventScope = root.eventScope
            )
        }
        return attachment
    }

    //? A container can't be focused by design. It is a branch node in the
    //? component hierarchy, and we only allow leaves to be focused.
    override fun acceptsFocus() = false
    override fun focusGiven(): UIEventResponse = Pass
    override fun focusTaken(): UIEventResponse = Pass
    final override fun asInternalComponent(): InternalContainer = this

    //! TODO: why is this UNKNOWN?
    override fun convertColorTheme(colorTheme: ColorTheme): ComponentStyleSet = ComponentStyleSet.UNKNOWN

    private fun checkIfCanAdd(component: Component): Pair<InternalComponent, Position> {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        require(component !== this) {
            "You can't add a component to itself."
        }
        require(component.isAttached.not()) {
            "Component $component is already attached to a parent. Please detach it first."
        }
        tileset.checkCompatibilityWith(component.tileset)
        val newPosition = component.position + contentBounds.position
        val newBounds = component.bounds.withPosition(newPosition)

        if (RuntimeConfig.config.shouldCheckBounds()) {
            require(contentBounds.containsBoundable(newBounds)) {
                """Adding out of bounds component $component  with content bounds $newBounds
                        |to the container $this with content bounds $contentBounds
                        | is not allowed.""".trimMargin()
            }
            children.firstOrNull { it.intersects(newBounds) }?.let {
                throw IllegalArgumentException(
                    """You can't add a component to a container which intersects with other components.
                        | $newBounds is intersecting with $component.""".trimMargin()
                )
            }
        }

        return component to newPosition
    }

    // TODO: test detachment
    override fun detachAllComponents() = attachments.toList().map { it.detach() }

    private inner class DefaultAttachedComponent(
        override val component: InternalComponent,
        override val parentContainer: InternalContainer
    ) : InternalAttachedComponent, InternalComponent by component {

        init {
            component.parent = parentContainer
            component.disabledProperty
                .updateFrom(
                    observable = parentContainer.disabledProperty,
                    bindingAction = component.bindingAction
                )
                .keepWhile(component.hasParent)
            component.hiddenProperty
                .updateFrom(
                    observable = parentContainer.hiddenProperty,
                    bindingAction = component.bindingAction
                )
                .keepWhile(component.hasParent)
            component.themeProperty
                .updateFrom(
                    observable = parentContainer.themeProperty,
                    bindingAction = component.bindingAction
                )
                .keepWhile(component.hasParent)
            component.tilesetProperty
                .updateFrom(
                    observable = parentContainer.tilesetProperty,
                    bindingAction = component.bindingAction
                )
                .keepWhile(component.hasParent)
        }

        override fun detach(): Component {
            val attachment = this
            component.parent = null
            this@DefaultContainer.children.remove(component)
            this@DefaultContainer.attachments.remove(attachment)
            component.resetState()
            component.root?.let { root ->
                component.flattenedTree.forEach { it.root = null }
                root.eventBus.publish(
                    event = ComponentRemoved(
                        parent = parentContainer,
                        component = component,
                        emitter = attachment
                    ),
                    eventScope = root.eventScope
                )
            }
            return component
        }
    }
}
