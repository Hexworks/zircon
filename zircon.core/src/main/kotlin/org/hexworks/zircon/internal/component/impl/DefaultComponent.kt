package org.hexworks.zircon.internal.component.impl

import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.DrawSurface
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.TilesetOverride
import org.hexworks.zircon.api.builder.graphics.LayerBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.hexworks.zircon.api.component.ComponentState
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.data.Bounds
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Identifier
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.internal.behavior.impl.DefaultBoundable
import org.hexworks.zircon.internal.component.InternalComponent
import org.hexworks.zircon.internal.component.WrappingStrategy
import org.hexworks.zircon.internal.event.ZirconEvent

@Suppress("UNCHECKED_CAST")
abstract class DefaultComponent(
        size: Size,
        tileset: TilesetResource,
        position: Position,
        private var componentStyles: ComponentStyleSet,
        private val wrappers: Iterable<WrappingStrategy>,
        private val graphic: TileGraphic = TileGraphicBuilder
                .newBuilder()
                .tileset(tileset)
                .size(size)
                .build(),
        private val boundable: DefaultBoundable = DefaultBoundable(
                size = size,
                position = position))
    : InternalComponent,
        Drawable by graphic,
        TilesetOverride by graphic {

    final override val id = Identifier.randomIdentifier()
    private var currentOffset = Position.defaultPosition()
    private var parent = Maybe.empty<Container>()

    init {
        applyStyle(componentStyles.getCurrentStyle())
        EventBus.listenTo<ZirconEvent.MouseOver>(id) {
            if (componentStyles.getCurrentStyle() != componentStyles.getStyleFor(ComponentState.MOUSE_OVER)) {
                applyStyle(componentStyles.applyMouseOverStyle())
            }
        }
        EventBus.listenTo<ZirconEvent.MouseOut>(id) {
            if (componentStyles.getCurrentStyle() != componentStyles.getStyleFor(ComponentState.DEFAULT)) {
                applyStyle(componentStyles.reset())
            }
        }
    }

    override fun parent() = parent

    override fun attachTo(parent: Container) {
        this.parent.map {
            it.removeComponent(this)
        }
        this.parent = Maybe.of(parent)
    }

    override fun absolutePosition(): Position {
        val parentPos = if (parent.isPresent) {
            parent.get().position()
        } else {
            Position.defaultPosition()
        }
        return position() + parentPos
    }

    override fun createCopy(): Layer {
        TODO("Creating copies of Components is not supported yet.")
    }

    override fun getRelativeTileAt(position: Position): Maybe<Tile> {
        return graphic.getTileAt(position)
    }

    override fun setRelativeTileAt(position: Position, tile: Tile) {
        graphic.setTileAt(position, tile)
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        position()
        return graphic.getTileAt(position.minus(position()))
    }

    override fun setTileAt(position: Position, tile: Tile) {
        graphic.setTileAt(position.minus(position()), tile)
    }

    override fun snapshot(): Map<Position, Tile> {
        return graphic.snapshot()
    }

    override fun fill(filler: Tile): Layer {
        graphic.fill(filler)
        return this
    }

    override fun draw(drawable: Drawable, position: Position) {
        graphic.draw(drawable, position)
    }

    final override fun moveTo(position: Position): Boolean {
        return boundable.moveTo(position)
    }

    override fun bounds() = this.boundable.bounds()

    override fun containsBoundable(boundable: Boundable) = this.boundable.containsBoundable(boundable)

    override fun containsPosition(position: Position) = boundable.containsPosition(position)

    override fun intersects(boundable: Boundable) = this.boundable.intersects(boundable)

    override fun position() = boundable.position()

    override fun drawOnto(surface: DrawSurface, position: Position) {
        surface.draw(graphic, position)
    }

    override fun fetchComponentByPosition(position: Position) =
            if (containsPosition(position)) {
                Maybe.of(this)
            } else {
                Maybe.empty<InternalComponent>()
            }

    override fun onMousePressed(callback: Consumer<MouseAction>) {
        EventBus.listenTo<ZirconEvent.MousePressed>(id) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun onMouseReleased(callback: Consumer<MouseAction>) {
        EventBus.listenTo<ZirconEvent.MouseReleased>(id) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun onMouseMoved(callback: Consumer<MouseAction>) {
        EventBus.listenTo<ZirconEvent.MouseMoved>(id) { (mouseAction) ->
            callback.accept(mouseAction)
        }
    }

    override fun componentStyleSet() = componentStyles

    override fun setComponentStyleSet(componentStyleSet: ComponentStyleSet,
                                      applyToEmptyCells: Boolean) {
        this.componentStyles = componentStyleSet
        applyStyle(componentStyleSet.getCurrentStyle())
    }

    final override fun applyStyle(styleSet: StyleSet) {
        applyWrappers()
        graphic.applyStyle(
                styleSet = styleSet,
                bounds = Bounds.create(
                        position = wrapperOffset(),
                        size = getEffectiveSize()))
    }

    override fun tileGraphic() = graphic

    /**
     * Returns the size which this component takes up without its wrappers.
     */
    override fun getEffectiveSize() = size() - wrappersSize()

    /**
     * Returns the position of this component offset by the wrappers it has.
     */
    override fun getEffectivePosition() = position() + wrapperOffset()

    open fun transformToLayers() =
            listOf(LayerBuilder.newBuilder()
                    .tileGraphic(graphic)
                    .offset(position())
                    .tileset(tileset())
                    .build())

    override fun size() = boundable.size()

    override fun toString(): String {
        return "${this::class.simpleName}(id=${id.toString().substring(0, 4)}," +
                "position=${position()})"
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
            it.apply(graphic, currSize, currentOffset, componentStyles.getCurrentStyle())
            currentOffset += it.getOffset()
        }
    }

    override fun wrapperOffset() = wrappers.map { it.getOffset() }.fold(Position.topLeftCorner(), Position::plus)

    /**
     * Calculate the size taken by all the wrappers.
     */
    override fun wrappersSize() = wrappers.map { it.getOccupiedSize() }.fold(Size.zero(), Size::plus)


}
