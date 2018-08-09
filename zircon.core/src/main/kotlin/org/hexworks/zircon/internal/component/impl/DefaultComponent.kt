package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.WrappingStrategy
import org.hexworks.zircon.internal.event.InternalEvent

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
        size: Size,
        tileset: TilesetResource,
        position: Position,
        private var attached: Boolean = false,
        private var componentStyleSet: ComponentStyleSet,
        private val wrappers: Iterable<WrappingStrategy>,
        private val drawSurface: TileGraphic = TileGraphicBuilder
                .newBuilder()
                .tileset(tileset)
                .size(size)
                .build(),
        private val boundable: DefaultBoundable = DefaultBoundable(
                size = size,
                position = position))
    : InternalComponent,
        Drawable by drawSurface,
        TilesetOverride by drawSurface {

    override val id = Identifier.randomIdentifier()
    private var currentOffset = Position.defaultPosition()

    init {
        drawSurface.setStyleFrom(componentStyleSet.getCurrentStyle())
        applyWrappers()
        EventBus.listenTo<InternalEvent.MouseOver>(id) {
            if (componentStyleSet.getCurrentStyle() != componentStyleSet.getStyleFor(ComponentState.MOUSE_OVER)) {
                drawSurface.applyStyle(componentStyleSet.mouseOver())
            }
        }
        EventBus.listenTo<InternalEvent.MouseOut>(id) {
            if (componentStyleSet.getCurrentStyle() != componentStyleSet.getStyleFor(ComponentState.DEFAULT)) {
                drawSurface.applyStyle(componentStyleSet.reset())
            }
        }
    }

    override fun createCopy(): Layer {
        throw UnsupportedOperationException("No.")
    }

    override fun getRelativeTileAt(position: Position): Maybe<Tile> {
        throw UnsupportedOperationException()
    }

    override fun setRelativeTileAt(position: Position, character: Tile) {
        throw UnsupportedOperationException()
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        throw UnsupportedOperationException()
    }

    override fun setTileAt(position: Position, tile: Tile) {
        throw UnsupportedOperationException()
    }

    override fun snapshot(): Map<Position, Tile> {
        TODO("not implemented")
    }

    override fun fill(filler: Tile): Layer {
        TODO("not implemented")
    }

    override fun draw(drawable: Drawable, position: Position) {
        throw UnsupportedOperationException()
    }

    override fun moveTo(position: Position): Boolean {
        return boundable.moveTo(position)
    }

    override fun isAttached() = attached

    override fun signalAttached() {
        this.attached = true
    }

    override fun containsBoundable(boundable: Boundable) = this.boundable.containsBoundable(boundable)

    override fun containsPosition(position: Position) = boundable.containsPosition(position)

    override fun intersects(boundable: Boundable) = this.boundable.intersects(boundable)

    override fun position() = boundable.position()

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.draw(drawSurface, boundable.position())
    }

    override fun fetchComponentByPosition(position: Position) =
            if (containsPosition(position)) {
                Maybe.of(this)
            } else {
                Maybe.empty<InternalComponent>()
            }

    override fun onMousePressed(callback: Consumer<MouseAction>) {
        EventBus.listenTo<InternalEvent.MousePressed>(id) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun onMouseReleased(callback: Consumer<MouseAction>) {
        EventBus.listenTo<InternalEvent.MouseReleased>(id) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun onMouseMoved(callback: Consumer<MouseAction>) {
        EventBus.listenTo<InternalEvent.MouseMoved>(id) { (mouseAction) ->
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
    override fun getEffectiveSize() = size() - getWrappersSize()

    /**
     * Returns the position of this component offset by the wrappers it has.
     */
    override fun getEffectivePosition() = position() + getWrapperOffset()

    /**
     * Returns the position from which themes should be applied.
     */
    fun getEffectiveThemeablePosition() = position() + getNonThemeableOffset()

    /**
     * Returns the offset which is caused by the wrappers of this component.
     * So basically this is the value of the component's position (`position()`)
     * plus the space which is taken up by the wrappers.
     */
    fun getWrapperOffset() = wrappers.map { it.getOffset() }.fold(Position.topLeftCorner()) { acc, position -> acc + position }

    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .textImage(drawSurface)
                    .offset(position())
                    .tileset(tileset())
                    .build())

    override fun size() = boundable.size()

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
    private fun getEffectiveThemeableSize() = size() - getNonThemedWrapperSize()

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
