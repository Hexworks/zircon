package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Container
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.component.InternalComponent
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*

open class DefaultContainer(initialSize: Size,
                            position: Position,
                            componentStyles: ComponentStyles,
                            wrappers: Iterable<WrappingStrategy> = listOf())
    : DefaultComponent(initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers), Container {

    private val components = mutableListOf<InternalComponent>()

    override fun addComponent(component: Component) {
        (component as? DefaultComponent)?.let { dc ->
            dc.setPosition(dc.getPosition() + getEffectivePosition())
            require(components.none { it.intersects(component) }) {
                "You can't add a component to a container which intersects with other components!"
            }
            // TODO: if the component has the same size and position it adds it!!!
            require(containsBoundable(component)) {
                "You can't add a component to a container which is not within its bounds " +
                        "(target size: ${getEffectiveSize()}, component size: ${component.getBoundableSize()})!"
            }
            components.add(dc)
            EventBus.emit(EventType.ComponentAddition)
        } ?: throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
    }

    override fun acceptsFocus(): Boolean {
        return false
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        return false
    }

    override fun takeFocus(input: Optional<Input>) {}

    override fun removeComponent(component: Component) {
        if (components.remove(component).not()) {
            components
                    .filter { it is Container }
                    .forEach { (it as Container).removeComponent(component) }
        }
        EventBus.emit(EventType.ComponentRemoval)
    }

    override fun transformToLayers(): List<Layer> {
        return mutableListOf(LayerBuilder.newBuilder()
                .textImage(getDrawSurface())
                .offset(getPosition())
                .build()).also {
            it.addAll(components.flatMap { (it as DefaultComponent).transformToLayers() })
        }
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(getDrawSurface(), getPosition())
        components.forEach {
            it.drawOnto(surface)
        }
    }

    override fun fetchComponentByPosition(position: Position) =
            if (this.containsPosition(position).not()) {
                Optional.empty()
            } else {
                components.map {
                    it.fetchComponentByPosition(position)
                }.filter {
                    it.isPresent
                }.let { hits ->
                    if (hits.isEmpty()) {
                        Optional.of(this)
                    } else {
                        hits.first()
                    }
                }
            }


    override fun toString(): String {
        return "${javaClass.simpleName}(id=${getId().toString().substring(0, 4)})"
    }

    override fun applyTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(colorTheme.getDarkBackgroundColor())
                        .build())
                .build())
        components.forEach {
            it.applyTheme(colorTheme)
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