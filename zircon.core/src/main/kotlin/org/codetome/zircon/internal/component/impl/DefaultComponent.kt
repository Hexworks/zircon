package org.codetome.zircon.internal.component.impl

import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.DrawSurface
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.TilesetOverride
import org.codetome.zircon.api.builder.graphics.LayerBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.component.ComponentState
import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.input.MouseAction
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.api.util.Identifier
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.impl.DefaultBoundable
import org.codetome.zircon.internal.behavior.impl.DefaultTilesetOverride
import org.codetome.zircon.internal.component.InternalComponent
import org.codetome.zircon.internal.component.WrappingStrategy
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus

abstract class DefaultComponent(initialSize: Size,
                                initialTileset: Tileset,
                                position: Position,
                                private var attached: Boolean = false,
                                private var componentStyleSet: ComponentStyleSet,
                                private val wrappers: Iterable<WrappingStrategy>,
                                private val tilesetOverride: TilesetOverride = DefaultTilesetOverride(
                                        initialTileset = initialTileset),
                                private val drawSurface: TileImage = TileImageBuilder.newBuilder()
                                        .size(initialSize)
                                        .build(),
                                private val boundable: DefaultBoundable = DefaultBoundable(
                                        size = initialSize,
                                        position = position))
    : InternalComponent, Drawable by drawSurface, TilesetOverride by tilesetOverride {

    private val id = Identifier.randomIdentifier()
    private var currentOffset = Position.defaultPosition()

    init {
        drawSurface.setStyleFrom(componentStyleSet.getCurrentStyle())
        applyWrappers()
        EventBus.listenTo<Event.MouseOver>(id) {
            if (componentStyleSet.getCurrentStyle() != componentStyleSet.getStyleFor(ComponentState.MOUSE_OVER)) {
                drawSurface.applyStyle(componentStyleSet.mouseOver())
                EventBus.broadcast(Event.ComponentChange)
            }
        }
        EventBus.listenTo<Event.MouseOut>(id) {
            if (componentStyleSet.getCurrentStyle() != componentStyleSet.getStyleFor(ComponentState.DEFAULT)) {
                drawSurface.applyStyle(componentStyleSet.reset())
                EventBus.broadcast(Event.ComponentChange)
            }
        }
    }

    override fun isAttached() = attached

    override fun signalAttached() {
        this.attached = true
    }

    override fun setPosition(position: Position) {
        boundable.moveTo(position)
    }

    override fun containsBoundable(boundable: Boundable) = this.boundable.containsBoundable(boundable)

    override fun containsPosition(position: Position) = boundable.containsPosition(position)

    override fun intersects(boundable: Boundable) = this.boundable.intersects(boundable)

    override fun getId() = id

    override fun getPosition() = boundable.getPosition()

    override fun drawOnto(surface: DrawSurface, offset: Position) {
        surface.draw(drawSurface, boundable.getPosition())
    }

    override fun fetchComponentByPosition(position: Position) =
            if (containsPosition(position)) {
                Maybe.of(this)
            } else {
                Maybe.empty<InternalComponent>()
            }

    override fun onMousePressed(callback: Consumer<MouseAction>) {
        EventBus.listenTo<Event.MousePressed>(getId()) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun onMouseReleased(callback: Consumer<MouseAction>) {
        EventBus.listenTo<Event.MouseReleased>(getId()) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun onMouseMoved(callback: Consumer<MouseAction>) {
        EventBus.listenTo<Event.MouseMoved>(getId()) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun getComponentStyles() = componentStyleSet

    override fun setComponentStyles(componentStyleSet: ComponentStyleSet) {
        this.componentStyleSet = componentStyleSet

        drawSurface.applyStyle(componentStyleSet.getCurrentStyle(), getNonThemeableOffset(), getEffectiveThemeableSize())
    }

    fun getBoundable() = boundable

    fun getDrawSurface() = drawSurface

    /**
     * Returns the size which this component takes up without its wrappers.
     */
    override fun getEffectiveSize() = getBoundableSize() - getWrappersSize()

    /**
     * Returns the position of this component offset by the wrappers it has.
     */
    override fun getEffectivePosition() = getPosition() + getWrapperOffset()

    /**
     * Returns the position from which themes should be applied.
     */
    fun getEffectiveThemeablePosition() = getPosition() + getNonThemeableOffset()

    /**
     * Returns the offset which is caused by the wrappers of this component.
     * So basically this is the value of the component's position (`getPosition()`)
     * plus the space which is taken up by the wrappers.
     */
    fun getWrapperOffset() = wrappers.map { it.getOffset() }.fold(Position.topLeftCorner()) { acc, position -> acc + position }

    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .textImage(drawSurface)
                    .offset(getPosition())
                    .font(getCurrentFont())
                    .build())

    override fun getBoundableSize() = boundable.getBoundableSize()

    override fun toString(): String {
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)})"
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (this::class != other!!::class) return false
        other as DefaultComponent
        if (id != other.id) return false
        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }

    private fun applyWrappers() {
        var currSize = getEffectiveSize()
        currentOffset = Position.defaultPosition()
        wrappers.forEach {
            currSize += it.getOccupiedSize()
            it.apply(drawSurface, currSize, currentOffset, componentStyleSet.getCurrentStyle())
            currentOffset += it.getOffset()
        }
    }

    /**
     * Returns the size which this component takes up with only its themeable wrappers.
     */
    private fun getEffectiveThemeableSize() = getBoundableSize() - getNonThemedWrapperSize()

    /**
     * Calculate the size taken by all the wrappers.
     */
    private fun getWrappersSize() = wrappers.map { it.getOccupiedSize() }.fold(Size.zero()) { acc, size -> acc + size }

    /**
     * Returns the size of all wrappers which are not themeable.
     */
    private fun getNonThemedWrapperSize() = wrappers
            .filter { it.isThemeNeutral() }
            .map { it.getOccupiedSize() }
            .fold(Size.zero()) { acc, size -> acc + size }

    private fun getNonThemeableOffset() = wrappers
            .filter { it.isThemeNeutral() }
            .map { it.getOffset() }
            .fold(Position.topLeftCorner()) { acc, position -> acc + position }

}
