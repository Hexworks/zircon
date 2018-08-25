package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.builder.component.ComponentStyleSetBuilder
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.WrappingStrategy
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.event.InternalEvent

@Suppress("UNCHECKED_CAST")
open class DefaultContainer(initialSize: Size,
                            position: Position,
                            initialTileset: TilesetResource,
                            componentStyleSet: ComponentStyleSet,
                            wrappers: Iterable<WrappingStrategy> = listOf())
    : DefaultComponent(size = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        tileset = initialTileset),
        Container {

    private val components = mutableListOf<InternalComponent>()


    override fun draw(drawable: Drawable, position: Position) {
        if (drawable is Component) {
            require(position == drawable.position()) {
                "The component to draw has different position (${drawable.position()}) than the " +
                        "position given to the draw operation ($position). You may consider using " +
                        "the addComponent function instead."
            }
            addComponent(drawable)
        } else {
            super.draw(drawable, position)
        }
    }

    override fun addComponent(component: Component) {
        // TODO: refactor component to be a layer
        // TODO: and use the layering functionality out of the box
        require(component !== this) {
            "You can't add a component to itself!"
        }
        (component as? DefaultComponent)?.let { dc ->
            if (isAttached()) {
                dc.moveTo(dc.position() + getEffectivePosition())
            }
            if (RuntimeConfig.config.betaEnabled.not()) {
                require(tileset().size() == component.tileset().size()) {
                    "Trying to add component with incompatible tileset size '${component.tileset().size()}' to" +
                            "container with tileset size: '${tileset().size()}'!"
                }
                require(components.none { it.intersects(component) }) {
                    "You can't add a component to a container which intersects with other components!"
                }
            }
            components.add(dc)
            EventBus.broadcast(InternalEvent.ComponentAddition)
        } ?: throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
    }

    override fun moveTo(position: Position): Boolean {
        super.moveTo(position)
        components.forEach { comp ->
            comp.moveTo(comp.position() + getEffectivePosition())
            // TODO: if the component has the same size and position it adds it!!!
            if (RuntimeConfig.config.betaEnabled.not()) {
                require(containsBoundable(comp)) {
                    "You can't add a component to a container which is not within its bounds " +
                            "(target size: ${getEffectiveSize()}, component size: ${comp.size()}" +
                            ", position: ${comp.position()})!"
                }
            }
        }
        return true
    }

    override fun signalAttached() {
        super.signalAttached()
        components.forEach {
            it.signalAttached()
        }
    }

    override fun acceptsFocus() = false

    override fun giveFocus(input: Maybe<Input>) = false

    override fun takeFocus(input: Maybe<Input>) {}

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
            EventBus.broadcast(InternalEvent.ComponentRemoval)
        }
        return removalHappened
    }

    override fun transformToLayers(): List<Layer> {
        return mutableListOf(LayerBuilder.newBuilder()
                .tileGraphic(getDrawSurface())
                .offset(position())
                .build()).also {
            it.addAll(components.flatMap { (it as DefaultComponent).transformToLayers() })
        }
    }

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.draw(getDrawSurface(), position())
        components.forEach {
            it.drawOnto(surface)
        }
    }

    override fun fetchComponentByPosition(position: Position) =
            if (this.containsPosition(position).not()) {
                Maybe.empty()
            } else {
                components.map {
                    it.fetchComponentByPosition(position)
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
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)})"
    }

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.secondaryForegroundColor())
                        .backgroundColor(colorTheme.secondaryBackgroundColor())
                        .build())
                .build())
        components.forEach {
            it.applyColorTheme(colorTheme)
        }
    }

    fun getComponents() = components

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
