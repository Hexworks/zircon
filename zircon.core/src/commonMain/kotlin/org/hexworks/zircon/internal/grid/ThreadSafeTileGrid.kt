package org.hexworks.zircon.internal.grid

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileComposite
import org.hexworks.zircon.api.view.ViewContainer
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.animation.DefaultAnimationHandler
import org.hexworks.zircon.internal.animation.InternalAnimationHandler
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.InternalLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultShutdownHook
import org.hexworks.zircon.internal.behavior.impl.ThreadSafeLayerable
import org.hexworks.zircon.internal.data.LayerState
import org.hexworks.zircon.internal.extensions.cancelAll
import org.hexworks.zircon.internal.graphics.ThreadSafeTileGraphics
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import kotlin.jvm.Synchronized

class ThreadSafeTileGrid(
        initialTileset: TilesetResource,
        initialSize: Size,
        override var layerable: InternalLayerable = buildLayerable(initialSize),
        override var animationHandler: InternalAnimationHandler = DefaultAnimationHandler(),
        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                initialCursorSpace = initialSize),
        private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault())
    : InternalTileGrid,
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

    override val layerStates: Iterable<LayerState>
        @Synchronized
        get() {
            return layerable.layerStates
        }

    override val layers: Iterable<Layer>
        get() {
            return layerable.layers
        }

    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    private val subscriptions: MutableList<Subscription> = mutableListOf()

    override fun getTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position)
    }

    override fun getLayerAt(index: Int) = layerable.getLayerAt(index)

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
        subscriptions.cancelAll()
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
    override fun startAnimation(animation: Animation): AnimationInfo {
        return animationHandler.startAnimation(animation)
    }

    @Synchronized
    override fun stopAnimation(animation: Animation) {
        animationHandler.stopAnimation(animation)
    }

    @Synchronized
    override fun updateAnimations(currentTimeMs: Long, layerable: Layerable) {
        animationHandler.updateAnimations(currentTimeMs, layerable)
    }

    // DRAW SURFACE

    @Synchronized
    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        backend.transformTileAt(position, tileTransformer)
    }

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

    @Synchronized
    override fun fill(filler: Tile) {
        backend.fill(filler)
    }

    override fun toTileImage() = backend.toTileImage()

    override fun transform(transformer: (Position, Tile) -> Tile) {
        backend.transform(transformer)
    }

    override fun applyStyle(styleSet: StyleSet) {
        backend.applyStyle(styleSet)
    }

    // LAYERABLE

    @Synchronized
    override fun addLayer(layer: Layer) {
        layerable.addLayer(layer)
    }

    @Synchronized
    override fun setLayerAt(index: Int, layer: Layer) {
        require(index != 0) {
            "Can't displace the base layer (index 0) of a TileGrid."
        }
        layerable.setLayerAt(index, layer)
    }

    @Synchronized
    override fun insertLayerAt(index: Int, layer: Layer) {
        require(index != 0) {
            "Can't displace the base layer (index 0) of a TileGrid."
        }
        layerable.insertLayerAt(index, layer)
    }

    @Synchronized
    override fun insertLayersAt(index: Int, layers: Collection<Layer>) {
        require(index != 0) {
            "Can't displace the base layer (index 0) of a TileGrid."
        }
        layerable.insertLayersAt(index, layers)
    }

    @Synchronized
    override fun removeLayer(layer: Layer) {
        require(layer != backend) {
            "Can't remove the base layer (index 0) of a TileGrid"
        }
        layerable.removeLayer(layer)
    }

    @Synchronized
    override fun removeLayerAt(index: Int) {
        require(index != 0) {
            "Can't remove the base layer (index 0) of a TileGrid."
        }
        layerable.removeLayerAt(index)
    }

    @Synchronized
    override fun removeLayers(layers: Collection<Layer>) {
        require(layers.none { it === backend }) {
            "Can't remove the base layer (index 0) of a TileGrid."
        }
        layerable.removeLayers(layers)
    }

    // TODO: regression test this (drop(0))
    @Synchronized
    override fun removeAllLayers() {
        layers.drop(1).forEach(this::removeLayer)
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
