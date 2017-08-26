package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.component.Component
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.component.listener.MouseListener
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*

abstract class DefaultComponent private constructor(private var position: Position,
                                                    private var componentStyles: ComponentStyles,
                                                    private val drawSurface: TextImage,
                                                    private val boundable: Boundable,
                                                    private val wrappers: Iterable<WrappingStrategy>)
    : Component, Drawable by drawSurface {

    private val id: UUID = UUID.randomUUID()
    private var currentOffset = Position.DEFAULT_POSITION

    constructor(initialSize: Size,
                position: Position,
                componentStyles: ComponentStyles,
                wrappers: Iterable<WrappingStrategy>) : this(
            drawSurface = TextImageBuilder.newBuilder()
                    .filler(TextCharacterBuilder.EMPTY)
                    .size(initialSize)
                    .build(),
            boundable = DefaultBoundable(
                    size = initialSize,
                    position = position),
            position = position,
            componentStyles = componentStyles,
            wrappers = wrappers)

    init {
        drawSurface.setStyleFrom(componentStyles.getCurrentStyle())
        var currSize = getEffectiveSize()
        currentOffset = Position.DEFAULT_POSITION
        wrappers.forEach {
            currSize += it.getOccupiedSize()
            it.apply(drawSurface, currSize, componentStyles.getCurrentStyle())
            currentOffset += it.getOffset()
        }
        EventBus.subscribe(EventType.MouseOver(id), {
            if (componentStyles.getCurrentStyle() != componentStyles.getStyleFor(ComponentState.MOUSE_OVER)) {
                drawSurface.applyStyle(componentStyles.mouseOver())
                EventBus.emit(EventType.ComponentChange)
            }
        })
        EventBus.subscribe(EventType.MouseOut(id), {
            if (componentStyles.getCurrentStyle() != componentStyles.getStyleFor(ComponentState.DEFAULT)) {
                drawSurface.applyStyle(componentStyles.reset())
                EventBus.emit(EventType.ComponentChange)
            }
        })
    }

    fun getDrawSurface() = drawSurface

    /**
     * Calculate the size taken by all the wrappers.
     */
    fun getWrappersSize() = wrappers.map { it.getOccupiedSize() }.fold(Size.ZERO) { acc, size -> acc + size }

    /**
     * Returns the size of all wrappers which are themeable
     */
    fun getThemedWrappersSize() = wrappers
            .filter { it.isThemeNeutral() }
            .map { it.getOccupiedSize() }
            .fold(Size.ZERO) { acc, size -> acc + size }

    /**
     * Returns the size which this component takes up without its wrappers.
     */
    fun getEffectiveSize() = getBoundableSize() - getWrappersSize()

    fun getEffectiveThemeableSize() = getBoundableSize() - getThemedWrappersSize()

    /**
     * Returns the position of this component offset by the wrappers it has.
     */
    fun getEffectivePosition() = getPosition() + getOffset()

    fun getEffectiveThemeablePosition() = getPosition() + getThemeableOffset()

    /**
     * Returns the offset which is caused by the wrappers of this component.
     */
    fun getOffset() = wrappers.map { it.getOffset() }.fold(Position.TOP_LEFT_CORNER) { acc, position -> acc + position }

    fun getThemeableOffset() = wrappers
            .filter { it.isThemeNeutral() }
            .map { it.getOffset() }
            .fold(Position.TOP_LEFT_CORNER) { acc, position -> acc + position }

    fun getCurrentOffset() = currentOffset

    fun setPosition(position: Position) {
        this.position = position
    }

    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .textImage(drawSurface)
                    .offset(getPosition())
                    .build())

    final override fun getBoundableSize() = boundable.getBoundableSize()

    override fun containsBoundable(boundable: Boundable) = this.boundable.containsBoundable(boundable)

    override fun containsPosition(position: Position) = boundable.containsPosition(position)

    override fun intersects(boundable: Boundable) = this.boundable.intersects(boundable)

    override fun getId() = id

    override fun getPosition() = position

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(drawSurface, position)
    }

    override fun drawOnto(destination: DrawSurface,
                          startRowIndex: Int,
                          rows: Int,
                          startColumnIndex: Int,
                          columns: Int,
                          destinationRowOffset: Int,
                          destinationColumnOffset: Int) {
        drawSurface.drawOnto(destination, startRowIndex, rows, startColumnIndex, columns,
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

    override fun getComponentStyles() = componentStyles

    override fun setComponentStyles(componentStyles: ComponentStyles) {
        this.componentStyles = componentStyles
        drawSurface.applyStyle(componentStyles.getCurrentStyle(), getThemeableOffset(), getEffectiveThemeableSize())
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