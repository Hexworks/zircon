package org.hexworks.zircon.internal.component.impl

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.collections.immutable.persistentMapOf
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.DisposeSubscription
import org.hexworks.cobalt.events.api.KeepSubscription
import org.hexworks.cobalt.events.api.subscribeTo
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalAttachedComponent
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentAdded
import org.hexworks.zircon.internal.event.ZirconEvent.ComponentRemoved
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.jvm.Synchronized

@Suppress("UNCHECKED_CAST")
open class DefaultContainer(
        componentMetadata: ComponentMetadata,
        renderer: ComponentRenderingStrategy<out Component>
) : InternalContainer, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderer) {

    private var componentLookup = persistentMapOf<UUID, InternalAttachedComponent>()

    final override var children: PersistentList<InternalComponent> = persistentListOf()
        private set

    override val descendants: Iterable<InternalComponent>
        @Synchronized
        get() {
            return children.flatMap { listOf(it).plus(it.descendants) }
        }

    override fun acceptsFocus() = false

    override fun focusGiven(): UIEventResponse = Pass

    override fun focusTaken(): UIEventResponse = Pass

    // TODO: test the hell out of this
    @Synchronized
    override fun moveTo(position: Position, signalComponentChange: Boolean) {
        val diff = position - this.position
        super.moveTo(position, signalComponentChange)
        children.forEach {
            it.moveTo(it.position + diff, false)
        }
    }

    @Synchronized
    override fun addComponent(component: Component): InternalAttachedComponent {

        val ic = performChecks(component)
        val attachment = DefaultAttachedComponent(ic, this)

        componentLookup = componentLookup.put(ic.id, attachment)
        children = children.add(ic)

        Zircon.eventBus.subscribeTo<ComponentRemoved>(ZirconScope) { (_, removedComponent) ->
            if (removedComponent == component) {
                componentLookup = componentLookup.remove(component.id)
                children = children.remove(ic)
                DisposeSubscription
            } else KeepSubscription
        }

        Zircon.eventBus.publish(
                event = ComponentAdded(
                        parent = this,
                        component = component,
                        emitter = this),
                eventScope = ZirconScope)

        return attachment
    }

    @Synchronized
    override fun fetchComponentByPosition(absolutePosition: Position) = if (this.containsPosition(absolutePosition).not()) {
        Maybe.empty()
    } else {
        componentLookup.values.map {
            it.fetchComponentByPosition(absolutePosition)
        }.filter {
            it.isPresent
        }.let { hits ->
            if (hits.isEmpty()) {
                Maybe.of(this)
            } else {
                hits.first()
            }
        }
    }

    @Synchronized
    override fun convertColorTheme(colorTheme: ColorTheme) = ComponentStyleSet.empty()

    override fun clear() {
        componentLookup.values.forEach { it.detach() }
    }

    private fun performChecks(component: Component): InternalComponent {
        require(component is InternalComponent) {
            "The supplied component does not implement required interface: InternalComponent."
        }
        require(component !== this) {
            "You can't add a component to itself."
        }
        require(component.descendants.none { it == component }) {
            "A component can't become its own descendant."
        }
        require(component.isAttached.not()) {
            "This component is already attached to a parent. Please detach it first."
        }
        val originalRect = component.rect
        component.moveTo(component.position + contentOffset + position)
        if (RuntimeConfig.config.debugMode.not()) {
            val contentBounds = contentSize.toRect()
            tileset.checkCompatibilityWith(component.tileset)
            require(contentBounds.containsBoundable(originalRect)) {
                "Adding out of bounds component (${component::class.simpleName}) " +
                        "with bounds ($originalRect) to the container (${this::class.simpleName}) " +
                        "with content bounds ($contentBounds) is not allowed."
            }
            children.firstOrNull { it.intersects(component) }?.let {
                throw IllegalArgumentException(
                        "You can't add a component to a container which intersects with other components. " +
                                "$it is intersecting with $component.")
            }
        }
        return component
    }

}
