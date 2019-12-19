package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.UIEventResponse
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope
import kotlin.jvm.Synchronized

@Suppress("UNCHECKED_CAST")
open class DefaultContainer(componentMetadata: ComponentMetadata,
                            renderer: ComponentRenderingStrategy<out Component>)
    : InternalContainer, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderer) {

    private val components = mutableListOf<InternalComponent>()

    override val children: List<InternalComponent>
        @Synchronized
        get() = components.toList()

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
    override fun addComponent(component: Component) {
        (component as? InternalComponent)?.let {
            require(component !== this) {
                "You can't add a component to itself."
            }
            require(component.descendants.none { it == component }) {
                "A component can't become its own descendant."
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
            // TODO: regression test this! order was changed! it was buggy when the
            // TODO: component was re-added to the same container!
            component.attachTo(this)
            components.add(component)
            Zircon.eventBus.publish(
                    event = ZirconEvent.ComponentAdded,
                    eventScope = ZirconScope)
        } ?: throw IllegalArgumentException(
                "The supplied component does not implement InternalComponent.")
    }

    @Synchronized
    override fun removeComponent(component: Component): Boolean {
        var removalHappened = components.remove(component)
        if (removalHappened.not()) {
            val childResults = components
                    .filterIsInstance<Container>()
                    .map { it.removeComponent(component) }
            removalHappened = if (childResults.isEmpty()) {
                false
            } else {
                childResults.reduce(Boolean::or)
            }
        }
        if (removalHappened) {
            // TODO: regression test this!
            component.detach()
            Zircon.eventBus.publish(
                    event = ZirconEvent.ComponentRemoved,
                    eventScope = ZirconScope)
        }
        return removalHappened
    }

    @Synchronized
    override fun detachAllComponents(): Boolean {
        val removalHappened = components.isNotEmpty()
        components.toList().forEach {
            removeComponent(it)
        }
        if (removalHappened) {
            Zircon.eventBus.publish(
                    event = ZirconEvent.ComponentRemoved,
                    eventScope = ZirconScope)
        }
        return removalHappened
    }

    @Synchronized
    override fun fetchComponentByPosition(absolutePosition: Position) =
            if (this.containsPosition(absolutePosition).not()) {
                Maybe.empty()
            } else {
                components.map {
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

}
