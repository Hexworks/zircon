package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.ComponentStyleSetBuilder
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.ColorTheme
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.component.Container
import org.codetome.zircon.api.font.Font
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.component.InternalComponent
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import org.codetome.zircon.internal.font.impl.FontSettings
import org.codetome.zircon.util.Maybe
import java.util.*

open class DefaultContainer(initialSize: Size,
                            position: Position,
                            initialFont: Font,
                            componentStyleSet: ComponentStyleSet,
                            wrappers: Iterable<WrappingStrategy> = listOf())
    : DefaultComponent(initialSize = initialSize,
        position = position,
        componentStyleSet = componentStyleSet,
        wrappers = wrappers,
        initialFont = initialFont), Container {

    private val components = mutableListOf<InternalComponent>()

    override fun addComponent(component: Component) {
        require(component !== this) {
            "You can't add a component to itself!"
        }
        (component as? DefaultComponent)?.let { dc ->
            if(isAttached()) {
                dc.setPosition(dc.getPosition() + getEffectivePosition())
            }
            if (component.getCurrentFont() === FontSettings.NO_FONT) {
                component.useFont(getCurrentFont())
            } else {
                require(getCurrentFont().getSize() == component.getCurrentFont().getSize()) {
                    "Trying to add component with incompatible font size '${component.getCurrentFont().getSize()}' to" +
                            "container with font size: '${getCurrentFont().getSize()}'!"
                }
            }
            require(components.none { it.intersects(component) }) {
                "You can't add a component to a container which intersects with other components!"
            }
            components.add(dc)
            EventBus.emit(EventType.ComponentAddition)
        } ?: throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
    }

    override fun setPosition(position: Position) {
        super.setPosition(position)
        components.forEach {
            it.setPosition(it.getPosition() + getEffectivePosition())
            // TODO: if the component has the same size and position it adds it!!!
            require(containsBoundable(it)) {
                "You can't add a component to a container which is not within its bounds " +
                        "(target size: ${getEffectiveSize()}, component size: ${it.getBoundableSize()}" +
                        ", position: ${it.getPosition()})!"
            }
        }
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
            EventBus.emit(EventType.ComponentRemoval)
        }
        return removalHappened
    }

    override fun transformToLayers(): List<Layer> {
        // TODO: persistent list here
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

    override fun applyColorTheme(colorTheme: ColorTheme) {
        setComponentStyles(ComponentStyleSetBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(colorTheme.getDarkForegroundColor())
                        .backgroundColor(colorTheme.getDarkBackgroundColor())
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
