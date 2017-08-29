package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.builder.ComponentStylesBuilder
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.StyleSetBuilder
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Container
import org.codetome.zircon.api.component.Theme
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.listener.MouseListener
import java.awt.Point
import java.awt.Rectangle
import java.util.*

open class DefaultContainer(initialSize: Size,
                            position: Position,
                            componentStyles: ComponentStyles,
                            wrappers: Iterable<WrappingStrategy> = listOf())
    : DefaultComponent(initialSize = initialSize,
        position = position,
        componentStyles = componentStyles,
        wrappers = wrappers), Container {

    private val components = mutableListOf<Component>()
    private val rect = Rectangle(getEffectivePosition().column, getEffectivePosition().row, getEffectiveSize().columns, getEffectiveSize().rows)

    override fun addComponent(component: Component) {
        require(components.none { it.intersects(component) }) {
            "You can't add a component to a container which intersects with other components!"
        }
        // TODO: if the component has the same size and position it adds it!!!
        require(containsBoundable(component)) {
            "You can't add a component to a container which is not within its bounds!"
        }
        components.add(component.also {
            (component as? DefaultComponent)?.setPosition(component.getPosition() + getEffectivePosition()) ?:
                    throw IllegalArgumentException("Using a base class other than DefaultComponent is not supported!")
        })
    }

    override fun acceptsFocus(): Boolean {
        return false
    }

    override fun giveFocus(input: Optional<Input>): Boolean {
        return false
    }

    override fun takeFocus(input: Optional<Input>) {
    }

    override fun removeComponent(component: Component) {
        if(components.remove(component).not()) {
            components
                    .filter { it is Container }
                    .forEach { (it as Container).removeComponent(component) }
        }
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


    override fun addMouseListener(mouseListener: MouseListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(id=${getId().toString().substring(0, 4)})"
    }

    override fun intersects(boundable: Boundable) = rect.intersects(
            createOtherRectangle(boundable))

    override fun containsPosition(position: Position): Boolean {
        return rect.contains(Point(position.column, position.row))
    }

    override fun containsBoundable(boundable: Boundable) = rect.contains(
            createOtherRectangle(boundable))

    override fun applyTheme(theme: Theme) {
        setComponentStyles(ComponentStylesBuilder.newBuilder()
                .defaultStyle(StyleSetBuilder.newBuilder()
                        .foregroundColor(theme.getDarkForegroundColor())
                        .backgroundColor(theme.getDarkBackgroundColor())
                        .build())
                .build())
        components.forEach {
            it.applyTheme(theme)
        }
    }

    fun getComponents() = components

    fun fetchFlattenedComponentTree(): List<Component> {
        val result = mutableListOf<Component>()
        components.forEach {
            result.add(it)
            if(it is DefaultContainer) {
                result.addAll(it.fetchFlattenedComponentTree())
            }
        }
        return result
    }

    private fun createOtherRectangle(boundable: Boundable): Rectangle {
        return Rectangle(
                boundable.getPosition().column + getEffectivePosition().column,
                boundable.getPosition().row + getEffectivePosition().row,
                boundable.getBoundableSize().columns,
                boundable.getBoundableSize().rows)
    }
}