package org.hexworks.zircon.internal.grid

import org.hexworks.cobalt.databinding.api.collection.ObservableList
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationHandle
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.view.ViewContainer
import org.hexworks.zircon.internal.animation.DefaultAnimationRunner
import org.hexworks.zircon.internal.animation.InternalAnimation
import org.hexworks.zircon.internal.animation.InternalAnimationRunner
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultShutdownHook
import org.hexworks.zircon.internal.behavior.impl.ThreadSafeLayerable
import org.hexworks.zircon.internal.graphics.PersistentTileGraphics
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import kotlin.jvm.Synchronized

class ThreadSafeTileGrid(
        initialTileset: TilesetResource,
        initialSize: Size,
        override var layerable: InternalLayerable = buildLayerable(initialSize),
        override var animationHandler: InternalAnimationRunner = DefaultAnimationRunner(),
        override var cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                initialCursorSpace = initialSize),
        private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault()
) : InternalTileGrid,
        ShutdownHook by DefaultShutdownHook(),
        UIEventProcessor by eventProcessor,
        ViewContainer by ViewContainer.create() {

    init {
        initializeLayerable(initialSize, initialTileset)
    }

    override var backend: Layer = layerable.getLayerAt(0).get()

    override val tiles: Map<Position, Tile>
        get() = backend.tiles

    override val size: Size
        get() = backend.size

    override var tileset: TilesetResource
        get() = backend.tileset
        set(value) {
            backend.tileset = value
        }

    override val tilesetProperty: Property<TilesetResource>
        get() = backend.tilesetProperty

    override val isClosed = false.toProperty()

    override val layers: ObservableList<out Layer>
        get() = layerable.layers

    private var originalCursorHandler = cursorHandler
    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    override fun fetchLayerStates() = layerable.fetchLayerStates()

    override fun getTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position)
    }

    @Synchronized
    override fun putTile(tile: Tile) {
        if (tile is CharacterTile && tile.character == '\n') {
            moveCursorToNextLine()
        } else {
            backend.draw(tile, cursorPosition)
            moveCursorForward()
        }
    }

    override var isCursorVisible: Boolean
        get() = cursorHandler.isCursorVisible
        set(value) {
            cursorHandler.isCursorVisible = value
        }
    override var cursorPosition: Position
        get() = cursorHandler.cursorPosition
        set(value) {
            cursorHandler.cursorPosition = value
        }
    override val isCursorAtTheEndOfTheLine: Boolean
        get() = cursorHandler.isCursorAtTheEndOfTheLine
    override val isCursorAtTheStartOfTheLine: Boolean
        get() = cursorHandler.isCursorAtTheStartOfTheLine
    override val isCursorAtTheFirstRow: Boolean
        get() = cursorHandler.isCursorAtTheFirstRow
    override val isCursorAtTheLastRow: Boolean
        get() = cursorHandler.isCursorAtTheLastRow

    override fun moveCursorForward() {
        cursorHandler.moveCursorForward()
    }

    override fun moveCursorBackward() {
        cursorHandler.moveCursorBackward()
    }

    @Synchronized
    override fun close() {
        animationHandler.close()
        isClosed.value = true
    }

    @Synchronized
    override fun delegateTo(tileGrid: InternalTileGrid) {
        backend = tileGrid.backend
        layerable = tileGrid.layerable
        animationHandler = tileGrid.animationHandler
        cursorHandler = tileGrid.cursorHandler
    }

    @Synchronized
    override fun reset() {
        backend = originalBackend
        layerable = originalLayerable
        animationHandler = originalAnimationHandler
        cursorHandler = originalCursorHandler
    }

    @Synchronized
    override fun clear() {
        backend.clear()
        layerable = buildLayerable(size)
        initializeLayerable(size, tileset)
        backend = layerable.getLayerAt(0).get()
    }

    // ANIMATION HANDLER

    override fun start(animation: Animation): AnimationHandle {
        return animationHandler.start(animation)
    }

    override fun stop(animation: InternalAnimation) {
        animationHandler.stop(animation)
    }

    override fun updateAnimations(currentTimeMs: Long, layerable: Layerable) {
        animationHandler.updateAnimations(currentTimeMs, layerable)
    }

    // DRAW SURFACE

    override fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        backend.draw(tileComposite, drawPosition, drawArea)
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition, drawArea)
    }

    override fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition)
    }

    override fun draw(tileComposite: TileComposite) {
        backend.draw(tileComposite)
    }

    override fun draw(tileComposite: TileComposite, drawPosition: Position) {
        backend.draw(tileComposite, drawPosition)
    }

    override fun draw(tileMap: Map<Position, Tile>) {
        backend.draw(tileMap)
    }

    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        backend.draw(tileMap, drawPosition)
    }

    override fun fill(filler: Tile) {
        backend.fill(filler)
    }

    override fun transform(transformer: (Position, Tile) -> Tile) {
        backend.transform(transformer)
    }

    override fun applyStyle(styleSet: StyleSet) {
        backend.applyStyle(styleSet)
    }

    // LAYERABLE

    override fun getLayerAt(level: Int) = layerable.getLayerAt(level)

    override fun addLayer(layer: Layer) = layerable.addLayer(layer)

    override fun insertLayerAt(level: Int, layer: Layer) = layerable.insertLayerAt(level, layer)

    override fun setLayerAt(level: Int, layer: Layer) = layerable.setLayerAt(level, layer)

    override fun removeLayer(layer: Layer): Boolean {
        return layerable.removeLayer(layer)
    }

    private fun moveCursorToNextLine() {
        cursorPosition = cursorPosition.withRelativeY(1).withX(0)
    }

    private fun initializeLayerable(initialSize: Size, initialTileset: TilesetResource) {
        layerable.addLayer(Layer.newBuilder()
                .withTileGraphics(PersistentTileGraphics(
                        initialSize = initialSize,
                        initialTileset = initialTileset))
                .build())
    }

}

private fun buildLayerable(initialSize: Size): ThreadSafeLayerable {
    return ThreadSafeLayerable(
            initialSize = initialSize)
}
