package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.persistentListOf
import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty

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
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved

@Suppress("UNCHECKED_CAST")
open class DefaultContainer(
    metadata: ComponentMetadata,
    renderer: ComponentRenderingStrategy<out Component>
) : InternalContainer, DefaultComponent(
    metadata = metadata,
    renderer = renderer
) {

    final override val children: ObservableList<InternalComponent> by lazy {
        persistentListOf<InternalComponent>().toProperty()
    }

    private val attachments = mutableListOf<AttachedComponent>()

    final override fun moveTo(position: Position): Boolean {
        val diff = position - this.position
        super.moveTo(position)
        children.forEach {
            it.moveTo(it.position + diff)
        }
        return true
    }

    /**
     * Note that this method can be overridden we'd like to advise against if it is possible.
     * The logic is complex, and you can easily get into a sorry state where the implementation
     * doesn't make sense. If you really need to override this please call `super.addComponent`
     * and let Zircon do the heavy lifting for you.
     */
    override fun addComponent(component: Component): InternalAttachedComponent {
        val ic = checkIfCanAdd(component)
        val parent = this
        val attachment = DefaultAttachedComponent(ic, parent)
        children.add(ic)
        attachments.add(attachment)
        root?.let { root ->
            ic.flattenedTree.forEach { it.root = root }
            root.eventBus.publish(
                event = ComponentAdded(
                    parent = parent,
                    component = ic,
                    emitter = ic
                ),
                eventScope = root.eventScope
            )
        }
        return attachment
    }

    // TODO: test detachment
    override fun detachAllComponents() = attachments.toList().map { it.detach() }

    final override fun asInternalComponent(): InternalContainer = this

    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSet.unknown()

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    private fun checkIfCanAdd(component: Component): InternalComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        require(component !== this) {
            "You can't add a component to itself."
        }
        require(component.isAttached.not()) {
            "Component $component is already attached to a parent. Please detach it first."
        }
        val originalRect = component.rect
        tileset.checkCompatibilityWith(component.tileset)
        val newPosition = component.absolutePosition + contentOffset + absolutePosition
        if (RuntimeConfig.config.shouldCheckBounds()) {
            val contentBounds = contentSize.toRect()
            require(contentBounds.containsBoundable(originalRect)) {
                """Adding out of bounds component $component 
                        |to the container $this with content bounds $contentBounds
                        | is not allowed.""".trimMargin()
            }
            val newRect = originalRect.withPosition(newPosition)
            children.firstOrNull { it.intersects(newRect) }?.let {
                throw IllegalArgumentException(
                    """You can't add a component to a container which intersects with other components.
                        | $newRect is intersecting with $component.""".trimMargin()
                )
            }
        }
        component.moveTo(newPosition)
        return component
    }

    private inner class DefaultAttachedComponent(
        override val component: InternalComponent,
        override val parentContainer: InternalContainer
    ) : InternalAttachedComponent, InternalComponent by component {

        init {
            component.parent = parentContainer
            component.disabledProperty
                .updateFrom(
                    observable = parentContainer.disabledProperty,
                    updateWhenBound = component.updateOnAttach
                )
                .keepWhile(component.hasParent)
            component.hiddenProperty
                .updateFrom(
                    observable = parentContainer.hiddenProperty,
                    updateWhenBound = component.updateOnAttach
                )
                .keepWhile(component.hasParent)
            component.themeProperty
                .updateFrom(
                    observable = parentContainer.themeProperty,
                    updateWhenBound = component.updateOnAttach
                )
                .keepWhile(component.hasParent)
            component.tilesetProperty
                .updateFrom(
                    observable = parentContainer.tilesetProperty,
                    updateWhenBound = component.updateOnAttach
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
