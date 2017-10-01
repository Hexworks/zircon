package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.Position
import org.codetome.zircon.api.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.builder.LayerBuilder
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.builder.TextImageBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyles
import org.codetome.zircon.api.graphics.TextImage
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.component.InternalComponent
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.event.EventType
import java.util.*
import java.util.function.Consumer

abstract class DefaultComponent(initialSize: Size,
                                private var position: Position,
                                private var componentStyles: ComponentStyles,
                                private val wrappers: Iterable<WrappingStrategy>,
                                private val drawSurface: TextImage = TextImageBuilder.newBuilder()
                                        .filler(TextCharacterBuilder.EMPTY)
                                        .size(initialSize)
                                        .build(),
                                private var boundable: Boundable = DefaultBoundable(
                                        size = initialSize,
                                        position = position))
    : InternalComponent, Drawable by drawSurface {

    private val id: UUID = UUID.randomUUID()
    private var currentOffset = Position.DEFAULT_POSITION

    init {
        drawSurface.setStyleFrom(componentStyles.getCurrentStyle())
        applyWrappers()
        EventBus.subscribe(EventType.MouseOver(id), {
            if (componentStyles.getCurrentStyle() != componentStyles.getStyleFor(ComponentState.MOUSE_OVER)) {
                drawSurface.applyColorsFromStyle(componentStyles.mouseOver())
                EventBus.emit(EventType.ComponentChange)
            }
        })
        EventBus.subscribe(EventType.MouseOut(id), {
            if (componentStyles.getCurrentStyle() != componentStyles.getStyleFor(ComponentState.DEFAULT)) {
                drawSurface.applyColorsFromStyle(componentStyles.reset())
                EventBus.emit(EventType.ComponentChange)
            }
        })
    }

    override fun setPosition(position: Position) {
        this.position = position
        this.boundable = DefaultBoundable(
                size = getBoundableSize(),
                position = position)
    }

    override fun containsBoundable(boundable: Boundable) = this.boundable.containsBoundable(boundable)

    override fun containsPosition(position: Position) = boundable.containsPosition(position)

    override fun intersects(boundable: Boundable) = this.boundable.intersects(boundable)

    override fun getId() = id

    override fun getPosition() = position

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(drawSurface, position)
    }

    override fun fetchComponentByPosition(position: Position) =
            if (containsPosition(position)) {
                Optional.of(this)
            } else {
                Optional.empty<InternalComponent>()
            }

    override fun onMousePressed(callback: Consumer<MouseAction>) {
        EventBus.subscribe<MouseAction>(EventType.MousePressed(getId()), { (mouseAction) ->
            callback.accept(mouseAction)
        })
    }

    override fun onMouseReleased(callback: Consumer<MouseAction>) {
        EventBus.subscribe<MouseAction>(EventType.MouseReleased(getId()), { (mouseAction) ->
            callback.accept(mouseAction)
        })
    }

    override fun getComponentStyles() = componentStyles

    override fun setComponentStyles(componentStyles: ComponentStyles) {
        this.componentStyles = componentStyles

        drawSurface.applyColorsFromStyle(componentStyles.getCurrentStyle(), getNonThemeableOffset(), getEffectiveThemeableSize())
    }

    fun getDrawSurface() = drawSurface

    /**
     * Calculate the size taken by all the wrappers.
     */
    fun getWrappersSize() = wrappers.map { it.getOccupiedSize() }.fold(Size.ZERO) { acc, size -> acc + size }

    /**
     * Returns the size of all wrappers which are not themeable.
     */
    fun getNonThemedWrapperSize() = wrappers
            .filter { it.isThemeNeutral() }
            .map { it.getOccupiedSize() }
            .fold(Size.ZERO) { acc, size -> acc + size }

    /**
     * Returns the size which this component takes up without its wrappers.
     */
    fun getEffectiveSize() = getBoundableSize() - getWrappersSize()

    /**
     * Returns the size which this component takes up with only its themeable wrappers.
     */
    fun getEffectiveThemeableSize() = getBoundableSize() - getNonThemedWrapperSize()

    /**
     * Returns the position of this component offset by the wrappers it has.
     */
    fun getEffectivePosition() = getPosition() + getWrapperOffset()

    /**
     * Returns the position from which themes should be applied.
     */
    fun getEffectiveThemeablePosition() = getPosition() + getNonThemeableOffset()

    /**
     * Returns the offset which is caused by the wrappers of this component.
     * So basically this is the value of the component's position (`getPosition()`)
     * plus the space which is taken up by the wrappers.
     */
    fun getWrapperOffset() = wrappers.map { it.getOffset() }.fold(Position.TOP_LEFT_CORNER) { acc, position -> acc + position }

    fun getNonThemeableOffset() = wrappers
            .filter { it.isThemeNeutral() }
            .map { it.getOffset() }
            .fold(Position.TOP_LEFT_CORNER) { acc, position -> acc + position }

    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .textImage(drawSurface)
                    .offset(getPosition())
                    .build())

    private fun applyWrappers() {
        var currSize = getEffectiveSize()
        currentOffset = Position.DEFAULT_POSITION
        wrappers.forEach {
            currSize += it.getOccupiedSize()
            it.apply(drawSurface, currSize, currentOffset, componentStyles.getCurrentStyle())
            currentOffset += it.getOffset()
        }
    }

    final override fun getBoundableSize() = boundable.getBoundableSize()

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