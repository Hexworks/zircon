package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.graphics.DrawSurface
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.internal.Zircon
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.InternalContainer
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.internal.event.ZirconScope

@Suppress("UNCHECKED_CAST")
open class DefaultContainer(componentMetadata: ComponentMetadata,
                            renderer: ComponentRenderingStrategy<out Component>)
    : InternalContainer, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderer) {

    override val children: List<Component>
        get() = components.toList()

    private val components = mutableListOf<InternalComponent>()

    override fun acceptsFocus() = false

    override fun draw(drawable: Drawable, position: Position) {
        if (drawable is Component) {
            require(position == drawable.position) {
                "The component to draw has different position (${drawable.position}) than the " +
                        "position given to the draw operation ($position). You may consider using " +
                        "the addComponent function instead."
            }
            addComponent(drawable)
        } else {
            super<DefaultComponent>.draw(drawable, position)
        }
    }

    override fun clear() {
        detachAllComponents()
    }

    override fun addComponent(component: Component) {
        require(component !== this) {
            "You can't add a component to itself!"
        }
        (component as? InternalComponent)?.let { dc ->
            // TODO: this is fishy, let's investigate whether
            // TODO: we can do this without moving the component
            val originalRect = dc.rect
            dc.moveTo(dc.position + contentPosition)
            if (RuntimeConfig.config.debugMode.not()) {
                val contentBounds = contentSize.toRect()
                require(currentTileset().size == component.currentTileset().size) {
                    "Trying to add component with incompatible tileset size '${component.currentTileset().size}' to" +
                            "container with tileset size: '${currentTileset().size}'!"
                }
                require(contentBounds.containsBoundable(originalRect)) {
                    "Adding out of bounds component (${component::class.simpleName}) " +
                            "with bounds ($originalRect) to the container (${this::class.simpleName}) " +
                            "with content bounds ($contentBounds) is not allowed."
                }
                children.firstOrNull { it.intersects(dc) }?.let {
                    throw IllegalArgumentException(
                            "You can't add a component to a container which intersects with other components. " +
                                    "$it is intersecting with $dc.")
                }
            }
            // TODO: regression test this! order was changed! it was buggy when the component was re-added to the
            // TODO: same container!
            dc.attachTo(this)
            components.add(dc)
            Zircon.eventBus.publish(
                    event = ZirconEvent.ComponentAddition,
                    eventScope = ZirconScope)
        } ?: throw IllegalArgumentException(
                "The supplied component does not implement InternalComponent.")
    }

    override fun removeComponent(component: Component): Boolean {
        var removalHappened = components.remove(component)
        if (removalHappened.not()) {
            val childResults = components
                    .filter { it is Container }
                    .map { (it as Container).removeComponent(component) }
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
                    event = ZirconEvent.ComponentRemoval,
                    eventScope = ZirconScope)
        }
        return removalHappened
    }

    override fun detachAllComponents(): Boolean {
        val removalHappened = components.isNotEmpty()
        components.forEach {
            removeComponent(it)
        }
        if (removalHappened) {
            Zircon.eventBus.publish(
                    event = ZirconEvent.ComponentRemoval,
                    eventScope = ZirconScope)
        }
        return removalHappened
    }

    override fun toFlattenedLayers(): Iterable<Layer> {
        return listOf(this).plus(components.flatMap { it.toFlattenedLayers() })
    }

    override fun toFlattenedComponents(): Iterable<InternalComponent> {
        return listOf(this).plus(components.flatMap { it.toFlattenedComponents() })
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.draw(graphics, position)
        components.forEach {
            it.drawOnto(surface)
        }
    }

    override fun fetchComponentByPosition(position: Position) =
            if (this.containsPosition(position).not()) {
                Maybe.empty()
            } else {
                components.map {
                    it.fetchComponentByPosition(position - this.position)
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

    override fun applyColorTheme(colorTheme: ColorTheme): ComponentStyleSet {
        return ComponentStyleSet.empty()
    }

    override fun render() {
    }
}
