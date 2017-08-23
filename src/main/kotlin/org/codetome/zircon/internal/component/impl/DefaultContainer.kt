package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.component.Container
import org.codetome.zircon.internal.component.listener.MouseListener
import java.util.*

class DefaultContainer private constructor(private val backend: DefaultComponent,
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

    fun getBackend() = backend

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