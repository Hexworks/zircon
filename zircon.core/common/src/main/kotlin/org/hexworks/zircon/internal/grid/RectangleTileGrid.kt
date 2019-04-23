package org.hexworks.zircon.internal.grid

import org.hexworks.cobalt.datatypes.Maybe
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Snapshot
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphics
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.api.util.TileTransformer
import org.hexworks.zircon.internal.animation.DefaultAnimationHandler
import org.hexworks.zircon.internal.animation.InternalAnimationHandler
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultShutdownHook
import org.hexworks.zircon.internal.extensions.cancelAll
import org.hexworks.zircon.internal.graphics.ConcurrentTileGraphics
import org.hexworks.zircon.internal.uievent.UIEventProcessor

class RectangleTileGrid(
        tileset: TilesetResource,
        size: Size,
        override var backend: TileGraphics = ConcurrentTileGraphics(
                size = size,
                tileset = tileset,
                styleSet = StyleSet.defaultStyle()),
        override var layerable: Layerable = DefaultLayerable(),
        override var animationHandler: InternalAnimationHandler = DefaultAnimationHandler(),
        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                cursorSpace = size),
        private val subscriptions: MutableList<Subscription> = mutableListOf(),
        private val eventProcessor: UIEventProcessor = UIEventProcessor.createDefault())
    : InternalTileGrid,
        InternalCursorHandler by cursorHandler,
        ShutdownHook by DefaultShutdownHook(),
        UIEventProcessor by eventProcessor {

    override val layers: List<Layer>
        get() = layerable.layers

    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    override fun putCharacter(c: Char) {
        if (TextUtils.isPrintableCharacter(c)) {
            putTile(TileBuilder.newBuilder()
                    .withCharacter(c)
                    .withForegroundColor(foregroundColor)
                    .withBackgroundColor(backgroundColor)
                    .withModifiers(modifiers)
                    .build())
        }
    }

    override fun putTile(tile: Tile) {
        if (tile is CharacterTile && tile.character == '\n') {
            moveCursorToNextLine()
        } else {
            backend.setTileAt(cursorPosition(), tile)
            moveCursorForward()
        }
    }

    override fun close() {
        animationHandler.close()
        subscriptions.cancelAll()
    }

    override fun delegateActionsTo(tileGrid: InternalTileGrid) {
        backend = tileGrid.backend
        layerable = tileGrid.layerable
        animationHandler = tileGrid.animationHandler
    }

    override fun reset() {
        backend = originalBackend
        layerable = originalLayerable
        animationHandler = originalAnimationHandler
    }

    override fun clear() {
        backend.clear()
        layerable = DefaultLayerable()
    }

    // note that we need all of the below functions here and can't delegate to the corresponding
    // objects because delegating to mutable vars is broken in Kotlin:
    // http://the-cogitator.com/2018/09/29/by-the-way-exploring-delegation-in-kotlin.html#the-pitfall-of-interface-delegation

    override val size: Size
        get() = backend.size

    override var foregroundColor: TileColor
        get() = backend.foregroundColor
        set(value) {
            backend.foregroundColor = value
        }
    override var backgroundColor: TileColor
        get() = backend.backgroundColor
        set(value) {
            backend.backgroundColor = value
        }
    override var modifiers: Set<Modifier>
        get() = backend.modifiers
        set(value) {
            backend.modifiers = value
        }

    override fun toStyleSet() = backend.toStyleSet()

    override fun setStyleFrom(source: StyleSet) = backend.setStyleFrom(source)

    override fun enableModifiers(modifiers: Set<Modifier>) = backend.enableModifiers(modifiers)

    override fun disableModifiers(modifiers: Set<Modifier>) = backend.disableModifiers(modifiers)

    override fun clearModifiers() = backend.clearModifiers()

    override fun startAnimation(animation: org.hexworks.zircon.api.animation.Animation): AnimationInfo {
        return animationHandler.startAnimation(animation)
    }

    override fun stopAnimation(animation: Animation) {
        animationHandler.stopAnimation(animation)
    }

    override fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid) {
        animationHandler.updateAnimations(currentTimeMs, tileGrid)
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position)
    }

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position, tile)
    }

    override fun transformTileAt(position: Position, tileTransformer: TileTransformer) {
        backend.transformTileAt(position, tileTransformer)
    }

    override fun createSnapshot(): Snapshot {
        return backend.createSnapshot()
    }

    override fun draw(drawable: Drawable, position: Position) {
        backend.draw(drawable, position)
    }

    override fun currentTileset(): TilesetResource {
        return backend.currentTileset()
    }

    override fun useTileset(tileset: TilesetResource) {
        backend.useTileset(tileset)
    }

    override fun pushLayer(layer: Layer) {
        layerable.pushLayer(layer)
    }

    override fun popLayer(): Maybe<Layer> {
        return layerable.popLayer()
    }

    override fun removeLayer(layer: Layer) {
        layerable.removeLayer(layer)
    }

    private fun moveCursorToNextLine() {
        putCursorAt(cursorPosition().withRelativeY(1).withX(0))
    }

}
