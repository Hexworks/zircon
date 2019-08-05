package org.hexworks.zircon.internal.grid

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.DrawSurfaceSnapshot
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.internal.animation.DefaultAnimationHandler
import org.hexworks.zircon.internal.animation.InternalAnimationHandler
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultShutdownHook
import org.hexworks.zircon.internal.extensions.cancelAll
import org.hexworks.zircon.internal.graphics.ThreadSafeTileGraphics
import org.hexworks.zircon.internal.uievent.UIEventProcessor
import org.hexworks.zircon.platform.util.Dispatchers

class RectangleTileGrid(
        tileset: TilesetResource,
        size: Size,
        override var backend: TileGraphics = ThreadSafeTileGraphics(
                size = size,
                tileset = tileset),
        override var layerable: Layerable = DefaultLayerable(),
        override var animationHandler: InternalAnimationHandler = DefaultAnimationHandler(),
        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                cursorSpace = size),
        private val subscriptions: MutableList<Subscription> = mutableListOf(),
        private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault())
    : InternalTileGrid,
        InternalCursorHandler by cursorHandler,
        ShutdownHook by DefaultShutdownHook(),
        UIEventProcessor by eventProcessor,
        CoroutineScope {

    // TODO: backend or backend + layers?
    override val tiles: Map<Position, Tile>
        get() = TODO("not implemented")

    override val layers: List<Layer>
        get() = layerable.layers

    override val coroutineContext = Dispatchers.Single

    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    override fun putTile(tile: Tile) {
        launch {
            if (tile is CharacterTile && tile.character == '\n') {
                moveCursorToNextLine()
            } else {
                backend.setTileAt(cursorPosition(), tile)
                moveCursorForward()
            }
        }
    }

    override fun close() {
        launch {
            animationHandler.close()
            subscriptions.cancelAll()
        }
    }

    override fun delegateTo(tileGrid: InternalTileGrid) {
        launch {
            backend = tileGrid.backend
            layerable = tileGrid.layerable
            animationHandler = tileGrid.animationHandler
        }
    }

    override fun reset() {
        launch {
            backend = originalBackend
            layerable = originalLayerable
            animationHandler = originalAnimationHandler
        }
    }

    override fun clear() {
        launch {
            backend.clear()
            layerable = DefaultLayerable()
        }
    }

    // note that we need all of the below functions here and can't delegate to the corresponding
    // objects because delegating to mutable vars is broken in Kotlin:
    // http://the-cogitator.com/2018/09/29/by-the-way-exploring-delegation-in-kotlin.html#the-pitfall-of-interface-delegation

    override val size: Size
        get() = backend.size

    ///////////////
    // ANIMATIONS
    ///////////////

    override fun startAnimation(animation: Animation): AnimationInfo {
        return animationHandler.startAnimation(animation)
    }

    override fun stopAnimation(animation: Animation) {
        animationHandler.stopAnimation(animation)
    }

    override fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid) {
        animationHandler.updateAnimations(currentTimeMs, tileGrid)
    }

    ///////////////////
    // TILE COMPOSITE
    ///////////////////

    override fun getTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position)
    }

    /////////////////
    // DRAW SURFACE
    /////////////////

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position, tile)
    }

    override fun transformTileAt(position: Position, tileTransformer: (Tile) -> Tile) {
        backend.transformTileAt(position, tileTransformer)
    }

    override fun createSnapshot(): DrawSurfaceSnapshot {
        return backend.createSnapshot()
    }

    override fun draw(drawable: Drawable, position: Position) {
        backend.draw(drawable, position)
    }

    /////////////////////
    // TILESET OVERRIDE
    /////////////////////

    override fun currentTileset(): TilesetResource {
        return backend.currentTileset()
    }

    override fun useTileset(tileset: TilesetResource) {
        backend.useTileset(tileset)
    }

    //////////////
    // LAYERABLE
    //////////////

    override fun addLayer(layer: Layer) {
        layerable.addLayer(layer)
    }

    override fun removeLayer(layer: Layer) {
        layerable.removeLayer(layer)
    }

    private fun moveCursorToNextLine() {
        putCursorAt(cursorPosition().withRelativeY(1).withX(0))
    }

}
