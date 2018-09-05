package org.hexworks.zircon.internal.grid

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.TileGraphicBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.event.EventBus
import org.hexworks.zircon.api.graphics.Layer
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.graphics.TileGraphic
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.modifier.Modifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.util.Consumer
import org.hexworks.zircon.api.util.Maybe
import org.hexworks.zircon.api.util.TextUtils
import org.hexworks.zircon.internal.animation.DefaultAnimationHandler
import org.hexworks.zircon.internal.animation.InternalAnimationHandler
import org.hexworks.zircon.internal.behavior.InternalCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultCursorHandler
import org.hexworks.zircon.internal.behavior.impl.DefaultLayerable
import org.hexworks.zircon.internal.behavior.impl.DefaultShutdownHook
import org.hexworks.zircon.internal.event.ZirconEvent
import java.util.concurrent.Executors

class ThreadSafeTileGrid(
        tileset: TilesetResource,
        size: Size,
        override var backend: TileGraphic = TileGraphicBuilder.newBuilder()
                .size(size)
                .tileset(tileset)
                .build(),
        override var layerable: Layerable = DefaultLayerable(size),
        override var animationHandler: InternalAnimationHandler = DefaultAnimationHandler(),
        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                cursorSpace = size),
        private val shutdownHook: ShutdownHook = DefaultShutdownHook())
    : InternalTileGrid,
        Boundable by backend,
        InternalCursorHandler by cursorHandler,
        ShutdownHook by shutdownHook {

    private val executor = Executors.newSingleThreadExecutor()

    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    override fun startAnimation(animation: Animation): AnimationInfo {
        return execute { animationHandler.startAnimation(animation) }
    }

    override fun stopAnimation(animation: Animation) {
        submit { animationHandler.stopAnimation(animation) }
    }

    override fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid) {
        submit {
            animationHandler.updateAnimations(currentTimeMs, tileGrid)
        }
    }

    override fun onInput(listener: Consumer<Input>) {
        EventBus.subscribe<ZirconEvent.Input> { (input) ->
            listener.accept(input)
        }
    }

    override fun putCharacter(c: Char) {
        submit {
            if (TextUtils.isPrintableCharacter(c)) {
                putTile(TileBuilder.newBuilder()
                        .character(c)
                        .foregroundColor(foregroundColor())
                        .backgroundColor(backgroundColor())
                        .modifiers(activeModifiers())
                        .build())
            }
        }
    }

    override fun putTile(tile: Tile) {
        submit {
            if (tile is CharacterTile && tile.character == '\n') {
                moveCursorToNextLine()
            } else {
                backend.setTileAt(cursorPosition(), tile)
                moveCursorForward()
            }
        }
    }

    override fun useContentsOf(tileGrid: InternalTileGrid) {
        submit {
            backend = tileGrid.backend
            layerable = tileGrid.layerable
            animationHandler = tileGrid.animationHandler
        }
    }

    override fun reset() {
        submit {
            backend = originalBackend
            layerable = originalLayerable
            animationHandler = originalAnimationHandler
        }
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return execute { backend.getTileAt(position) }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        return execute { backend.setTileAt(position, tile) }
    }

    override fun snapshot(): Map<Position, Tile> {
        return execute { backend.snapshot() }
    }

    override fun draw(drawable: Drawable, position: Position) {
        submit { backend.draw(drawable, position) }
    }

    override fun tileset(): TilesetResource {
        return backend.tileset()
    }

    override fun useTileset(tileset: TilesetResource) {
        backend.useTileset(tileset)
    }

    override fun pushLayer(layer: Layer) {
        submit { layerable.pushLayer(layer) }
    }

    override fun popLayer(): Maybe<Layer> {
        return execute { layerable.popLayer() }
    }

    override fun removeLayer(layer: Layer) {
        submit { layerable.removeLayer(layer) }
    }

    override fun getLayers(): List<Layer> {
        return execute { layerable.getLayers() }
    }

    override fun styleSet(): StyleSet {
        return execute { backend.styleSet() }
    }

    override fun backgroundColor(): TileColor {
        return execute { backend.backgroundColor() }
    }

    override fun setBackgroundColor(backgroundColor: TileColor) {
        submit { backend.setBackgroundColor(backgroundColor) }
    }

    override fun foregroundColor(): TileColor {
        return execute { backend.foregroundColor() }
    }

    override fun setForegroundColor(foregroundColor: TileColor) {
        submit { backend.setForegroundColor(foregroundColor) }
    }

    override fun enableModifiers(modifiers: Set<Modifier>) {
        submit { backend.enableModifiers(modifiers) }
    }

    override fun enableModifiers(vararg modifiers: Modifier) {
        enableModifiers(modifiers.toSet())
    }

    override fun disableModifiers(modifiers: Set<Modifier>) {
        submit { backend.disableModifiers(modifiers) }
    }

    override fun disableModifiers(vararg modifiers: Modifier) {
        disableModifiers(modifiers.toSet())
    }

    override fun setModifiers(modifiers: Set<Modifier>) {
        submit { backend.setModifiers(modifiers) }
    }

    override fun clearModifiers() {
        submit { backend.clearModifiers() }
    }

    override fun resetColorsAndModifiers() {
        submit { backend.resetColorsAndModifiers() }
    }

    override fun activeModifiers(): Set<Modifier> {
        return execute { backend.activeModifiers() }
    }

    override fun setStyleFrom(source: StyleSet) {
        submit { backend.setStyleFrom(source) }
    }

    override fun close() {
        // TODO: listen to close on event bus ?
    }

    override fun clear() {
        backend.clear()
        layerable = DefaultLayerable(backend.size())
    }

    private fun <T : Any> execute(fn: () -> T): T {
        return executor.submit<T> {
            fn()
        }.get()
    }

    private fun <T : Any> submit(fn: () -> T) {
        executor.submit {
            fn()
        }
    }

    private fun moveCursorToNextLine() {
        putCursorAt(cursorPosition().withRelativeY(1).withX(0))
    }

}
