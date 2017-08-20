package org.codetome.zircon.component.impl

import org.codetome.zircon.Position
import org.codetome.zircon.Size
import org.codetome.zircon.behavior.DrawSurface
import org.codetome.zircon.component.Component
import org.codetome.zircon.component.ComponentStyles
import org.codetome.zircon.component.Container
import org.codetome.zircon.component.listener.MouseListener
import java.util.*

class DefaultContainer private constructor(private val backend: Component,
                                           private val position: Position,
                                           private val componentStyles: ComponentStyles)
    : Container, Component by backend {

    private val components = mutableListOf<Component>()

    constructor(initialSize: Size,
                position: Position,
                componentStyles: ComponentStyles) : this(
            backend = DefaultComponent(
                    initialSize = initialSize,
                    position = position,
                    componentStyles = componentStyles),
            position = position,
            componentStyles = componentStyles)

    override fun addComponent(component: Component) {
        require(components.none { it.intersects(component) }) {
            "You can't add a component to a container which intersects with other components!"
        }
        require(containsBoundable(component)) {
            "You can't add a component to a container which is not within its bounds!"
        }
        components.add(component)
    }

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(backend, position)
        components.forEach {
            it.drawOnto(surface)
        }
    }

    override fun fetchComponentByPosition(position: Position) =
            if(this.containsPosition(position).not()) {
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

    override fun setComponentStyles(componentStyles: ComponentStyles) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(id=${getId().toString().substring(0, 4)})"
    }
}