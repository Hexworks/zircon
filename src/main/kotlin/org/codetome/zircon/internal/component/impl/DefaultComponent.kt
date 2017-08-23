package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.component.listener.MouseListener
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*
import java.util.function.Consumer

class DefaultComponent private constructor(private val backend: TextImage,
                                           private val boundable: Boundable,
                                           private var position: Position,
                                           private val componentStyles: ComponentStyles)
    : Component, Drawable by backend {

    private val id: UUID = UUID.randomUUID()

    constructor(initialSize: Size,
                position: Position,
                componentStyles: ComponentStyles) : this(
            backend = TextImageBuilder.newBuilder()
                    .filler(TextCharacterBuilder.newBuilder()
                            .styleSet(componentStyles.getCurrentStyle())
                            .build())
                    .size(initialSize)
                    .build(),
            boundable = DefaultBoundable(
                    size = initialSize,
                    position = position),
            position = position,
            componentStyles = componentStyles)

    init {
        backend.setStyleFrom(componentStyles.getCurrentStyle())
        EventBus.subscribe(EventType.MouseOver(id), {
            backend.applyStyle(componentStyles.mouseOver())
            EventBus.emit(EventType.ComponentChange)
        })
        EventBus.subscribe(EventType.MouseOut(id), {
            backend.applyStyle(componentStyles.reset())
            EventBus.emit(EventType.ComponentChange)
        })
    }

    fun getBackend() = backend

    override fun getBoundableSize() = boundable.getBoundableSize()

    override fun containsBoundable(boundable: Boundable) = this.boundable.containsBoundable(boundable)

    override fun containsPosition(position: Position) = boundable.containsPosition(position)

    override fun intersects(boundable: Boundable) = this.boundable.intersects(boundable)

    override fun getId() = id

    override fun getPosition() = position

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(backend, position)
    }

    override fun drawOnto(destination: DrawSurface,
                          startRowIndex: Int,
                          rows: Int,
                          startColumnIndex: Int,
                          columns: Int,
                          destinationRowOffset: Int,
                          destinationColumnOffset: Int) {
        backend.drawOnto(destination, startRowIndex, rows, startColumnIndex, columns,
                destinationRowOffset, destinationColumnOffset)
    }

    override fun fetchComponentByPosition(position: Position) =
            if (containsPosition(position)) {
                Optional.of(this)
            } else {
                Optional.empty<Component>()
            }

    override fun addMouseListener(mouseListener: MouseListener) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun setComponentStyles(componentStyles: ComponentStyles) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun toString(): String {
        return "${javaClass.simpleName}(id=${id.toString().substring(0, 4)})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false
        other as DefaultComponent
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}