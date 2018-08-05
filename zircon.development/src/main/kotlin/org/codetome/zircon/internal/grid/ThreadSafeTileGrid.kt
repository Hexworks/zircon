package org.codetome.zircon.internal.grid

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.CharacterTile
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.Layer
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.modifier.Modifier
import org.codetome.zircon.api.resource.TilesetResource
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.api.util.Maybe
import org.codetome.zircon.internal.behavior.InternalCursorHandler
import org.codetome.zircon.internal.behavior.ShutdownHook
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
import org.codetome.zircon.internal.behavior.impl.DefaultShutdownHook
import org.codetome.zircon.internal.event.InternalEvent
import org.codetome.zircon.api.event.EventBus
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.graphics.MapTileImage
import java.util.concurrent.Executors


class ThreadSafeTileGrid(
        tileset: TilesetResource<out Tile>,
        size: Size,
        override var backend: TileImage = MapTileImage(
                size = size,
                tileset = tileset,
                styleSet = StyleSet.defaultStyle()),
        override var layerable: Layerable = DefaultLayerable(size),
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

    override fun onInput(listener: Consumer<Input>) {
        EventBus.subscribe<InternalEvent.Input> { (input) ->
            listener.accept(input)
        }
    }

    override fun putCharacter(c: Char) {
        submit {
            if (TextUtils.isPrintableCharacter(c)) {
                putTile(TileBuilder.newBuilder()
                        .character(c)
                        .foregroundColor(getForegroundColor())
                        .backgroundColor(getBackgroundColor())
                        .modifiers(getActiveModifiers())
                        .build())
            }
        }
    }

    override fun putTile(tile: Tile) {
        submit {
            if (tile is CharacterTile && tile.character == '\n') {
                moveCursorToNextLine()
            } else {
                backend.setTileAt(getCursorPosition(), tile)
                setPositionDirty(getCursorPosition())
                moveCursorForward()
            }
        }
    }

    override fun useContentsOf(tileGrid: InternalTileGrid) {
        submit {
            backend = tileGrid.backend
            layerable = tileGrid.layerable
        }
    }

    override fun reset() {
        submit {
            backend = originalBackend
            layerable = originalLayerable
        }
    }

    override fun getTileAt(position: Position): Maybe<Tile> {
        return execute { backend.getTileAt(position) }
    }

    override fun setTileAt(position: Position, tile: Tile) {
        return execute { backend.setTileAt(position, tile) }
    }

    override fun createSnapshot(): Map<Position, Tile> {
        return execute { backend.createSnapshot() }
    }

    override fun draw(drawable: Drawable, offset: Position) {
        submit { backend.draw(drawable, offset) }
    }

    override fun tileset(): TilesetResource<out Tile> {
        return backend.tileset()
    }

    override fun useTileset(tileset: TilesetResource<out Tile>) {
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

    override fun toStyleSet(): StyleSet {
        return execute { backend.toStyleSet() }
    }

    override fun getBackgroundColor(): TextColor {
        return execute { backend.getBackgroundColor() }
    }

    override fun setBackgroundColor(backgroundColor: TextColor) {
        submit { backend.setBackgroundColor(backgroundColor) }
    }

    override fun getForegroundColor(): TextColor {
        return execute { backend.getForegroundColor() }
    }

    override fun setForegroundColor(foregroundColor: TextColor) {
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

    override fun getActiveModifiers(): Set<Modifier> {
        return execute { backend.getActiveModifiers() }
    }

    override fun setStyleFrom(source: StyleSet) {
        submit { backend.setStyleFrom(source) }
    }

    override fun close() {
        // TODO: listen to close on event bus ?
    }

    override fun clear() {
        backend.clear()
        layerable = DefaultLayerable(backend.getBoundableSize())
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
        putCursorAt(getCursorPosition().withRelativeY(1).withX(0))
    }

}
