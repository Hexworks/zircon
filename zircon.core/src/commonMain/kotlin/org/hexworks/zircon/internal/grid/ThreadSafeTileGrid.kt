package org.hexworks.zircon.internal.grid

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
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.graphics.ThreadSafeTileGraphics
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import kotlin.jvm.Synchronized

class ThreadSafeTileGrid(
        initialTileset: TilesetResource,
        initialSize: Size,
        override var layerable: InternalLayerable = buildLayerable(initialSize),
        override var animationHandler: InternalAnimationRunner = DefaultAnimationRunner(),
        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                initialCursorSpace = initialSize),
        private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault()
) : InternalTileGrid,
        InternalCursorHandler by cursorHandler,
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
        @Synchronized
        set(value) {
            backend.tileset = value
        }

    override val tilesetProperty: Property<TilesetResource>
        get() = backend.tilesetProperty

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() {
            return layerable.layerStates
        }

    override val isClosed = false.toProperty()

    override val layers: Iterable<Layer>
        get() {
            return layerable.layers
        }

    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

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
    }

    @Synchronized
    override fun reset() {
        backend = originalBackend
        layerable = originalLayerable
        animationHandler = originalAnimationHandler
    }

    @Synchronized
    override fun clear() {
        backend.clear()
        layerable = buildLayerable(size)
        initializeLayerable(size, tileset)
    }

    // ANIMATION HANDLER

    @Synchronized
    override fun start(animation: Animation): AnimationHandle {
        return animationHandler.start(animation)
    }

    @Synchronized
    override fun stop(animation: InternalAnimation) {
        animationHandler.stop(animation)
    }

    @Synchronized
    override fun updateAnimations(currentTimeMs: Long, layerable: Layerable) {
        animationHandler.updateAnimations(currentTimeMs, layerable)
    }

    // DRAW SURFACE

    @Synchronized
    override fun draw(tileComposite: TileComposite, drawPosition: Position, drawArea: Size) {
        backend.draw(tileComposite, drawPosition, drawArea)
    }

    @Synchronized
    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position, drawArea: Size) {
        backend.draw(tileMap, drawPosition, drawArea)
    }

    @Synchronized
    override fun draw(tile: Tile, drawPosition: Position) {
        backend.draw(tile, drawPosition)
    }

    @Synchronized
    override fun draw(tileComposite: TileComposite) {
        backend.draw(tileComposite)
    }

    @Synchronized
    override fun draw(tileComposite: TileComposite, drawPosition: Position) {
        backend.draw(tileComposite, drawPosition)
    }

    @Synchronized
    override fun draw(tileMap: Map<Position, Tile>) {
        backend.draw(tileMap)
    }

    @Synchronized
    override fun draw(tileMap: Map<Position, Tile>, drawPosition: Position) {
        backend.draw(tileMap, drawPosition)
    }

    @Synchronized
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

    @Synchronized
    override fun getLayerAt(level: Int) = layerable.getLayerAt(level)

    @Synchronized
    override fun addLayer(layer: Layer) = layerable.addLayer(layer)

    @Synchronized
    override fun insertLayerAt(level: Int, layer: Layer) = layerable.insertLayerAt(level, layer)

    @Synchronized
    override fun setLayerAt(level: Int, layer: Layer) = layerable.setLayerAt(level, layer)

    override fun removeLayer(layer: Layer): Layer {
        return layerable.removeLayer(layer)
    }

    private fun moveCursorToNextLine() {
        cursorPosition = cursorPosition.withRelativeY(1).withX(0)
    }

    private fun initializeLayerable(initialSize: Size, initialTileset: TilesetResource) {
        layerable.addLayer(Layer.newBuilder()
                .withTileGraphics(ThreadSafeTileGraphics(
                        initialSize = initialSize,
                        initialTileset = initialTileset))
                .build())
    }

}

private fun buildLayerable(initialSize: Size): ThreadSafeLayerable {
    return ThreadSafeLayerable(
            initialSize = initialSize)
}
