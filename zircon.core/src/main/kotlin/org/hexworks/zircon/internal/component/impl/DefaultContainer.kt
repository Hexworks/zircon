package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.ZirconEvent
import org.hexworks.zircon.platform.factory.ThreadSafeQueueFactory

@Suppress("UNCHECKED_CAST")
abstract class DefaultContainer(componentMetadata: ComponentMetadata,
                                renderer: ComponentRenderingStrategy<out Component>)
    : Container, DefaultComponent(
        componentMetadata = componentMetadata,
        renderer = renderer) {

    private val components = ThreadSafeQueueFactory.create<InternalComponent>()

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

    override val children: List<Component>
        get() = components.toList()

    override fun addComponent(component: Component) {
        require(component !== this) {
            "You can't add a component to itself!"
        }
        (component as? DefaultComponent)?.let { dc ->
            dc.moveTo(dc.position + contentPosition)
            if (RuntimeConfig.config.betaEnabled.not()) {
                require(currentTileset().size == component.currentTileset().size) {
                    "Trying to add component with incompatible tileset size '${component.currentTileset().size}' to" +
                            "container with tileset size: '${currentTileset().size}'!"
                }
                val contentBounds = contentSize.toBounds()
                val originalDcBounds = dc.rect.withPosition(dc.position - contentPosition)
                require(contentBounds.containsBoundable(originalDcBounds)) {
                    "Trying to add a component ($component) with bounds($originalDcBounds)" +
                            " to a container ($this) with content bounds ($contentBounds) which is out of bounds."
                }
                components.firstOrNull { it.intersects(component) }?.let {
                    throw IllegalArgumentException(
                            "You can't add a component ($component) to a container which intersects with another component ($it)!")
                }
            }
            components.add(dc)
            dc.attachTo(this)
            EventBus.broadcast(ZirconEvent.ComponentAddition)
        } ?: throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
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
            EventBus.broadcast(ZirconEvent.ComponentRemoval)
        }
        return removalHappened
    }

    override fun transformToLayers(): List<Layer> {
        return listOf(LayerBuilder.newBuilder()
                .tileGraphic(tileGraphics)
                .offset(position)
                .build())
                .flatMap { layer ->
                    listOf(layer).plus(
                            components.flatMap {
                                (it as DefaultComponent).transformToLayers()
                                        .map { childLayer ->
                                            childLayer.moveTo(childLayer.position + position)
                                            childLayer
                                        }
                            })
                }
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.draw(tileGraphics, position)
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


    override fun toString(): String {
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)}," +
                "position=$position," +
                "size=$size," +
                "components=[${components.joinToString()}])"
    }

    fun fetchFlattenedComponentTree(): List<InternalComponent> {
        val result = mutableListOf<InternalComponent>()
        components.forEach {
            result.add(it)
            if (it is DefaultContainer) {
                result.addAll(it.fetchFlattenedComponentTree())
            }
        }
        return result
    }
}
