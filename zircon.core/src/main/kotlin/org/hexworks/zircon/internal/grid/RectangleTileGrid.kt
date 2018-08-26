package org.hexworks.zircon.internal.grid

import org.hexworks.zircon.api.animation.Animation
import org.hexworks.zircon.api.animation.AnimationInfo
import org.hexworks.zircon.api.behavior.Boundable
import org.hexworks.zircon.api.behavior.Drawable
import org.hexworks.zircon.api.behavior.Layerable
import org.hexworks.zircon.api.behavior.ShutdownHook
import org.hexworks.zircon.api.builder.data.TileBuilder
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
import org.hexworks.zircon.internal.graphics.ConcurrentTileGraphic


class RectangleTileGrid(
        tileset: TilesetResource,
        size: Size,
        override var backend: TileGraphic = ConcurrentTileGraphic(
                size = size,
                tileset = tileset,
                styleSet = StyleSet.defaultStyle()),
        override var layerable: Layerable = DefaultLayerable(size),
        override var animationHandler: InternalAnimationHandler = DefaultAnimationHandler(),
        private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                cursorSpace = size),
        private val shutdownHook: ShutdownHook = DefaultShutdownHook())
    : InternalTileGrid,
        InternalCursorHandler by cursorHandler,
        ShutdownHook by shutdownHook {

    private var originalBackend = backend
    private var originalLayerable = layerable
    private var originalAnimationHandler = animationHandler

    override fun startAnimation(animation: org.hexworks.zircon.api.animation.Animation): AnimationInfo {
        return animationHandler.startAnimation(animation)
    }

    override fun stopAnimation(animation: Animation) {
        animationHandler.stopAnimation(animation)
    }

    override fun updateAnimations(currentTimeMs: Long, tileGrid: TileGrid) {
        animationHandler.updateAnimations(currentTimeMs, tileGrid)
    }

    override fun onInput(listener: Consumer<Input>) {
        EventBus.subscribe<ZirconEvent.Input> { (input) ->
            listener.accept(input)
        }
    }

    override fun putCharacter(c: Char) {
        if (TextUtils.isPrintableCharacter(c)) {
            putTile(TileBuilder.newBuilder()
                    .character(c)
                    .foregroundColor(foregroundColor())
                    .backgroundColor(backgroundColor())
                    .modifiers(activeModifiers())
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

    override fun getTileAt(position: Position): Maybe<Tile> {
        return backend.getTileAt(position)
    }

    override fun setTileAt(position: Position, tile: Tile) {
        backend.setTileAt(position, tile)
    }

    override fun snapshot(): Map<Position, Tile> {
        return backend.snapshot()
    }

    override fun draw(drawable: Drawable, position: Position) {
        backend.draw(drawable, position)
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

    override fun getLayers(): List<Layer> {
        return layerable.getLayers()
    }

    override fun size(): Size {
        return backend.size()
    }

    override fun intersects(boundable: Boundable): Boolean {
        return backend.intersects(boundable)
    }

    override fun containsPosition(position: Position): Boolean {
        return backend.containsPosition(position)
    }

    override fun containsBoundable(boundable: Boundable): Boolean {
        return backend.containsBoundable(boundable)
    }

    override fun tileset(): TilesetResource {
        return backend.tileset()
    }

    override fun useTileset(tileset: TilesetResource) {
        backend.useTileset(tileset)
    }

    override fun styleSet(): StyleSet {
        return backend.styleSet()
    }

    override fun backgroundColor(): TileColor {
        return backend.backgroundColor()
    }

    override fun setBackgroundColor(backgroundColor: TileColor) {
        backend.setBackgroundColor(backgroundColor)
    }

    override fun foregroundColor(): TileColor {
        return backend.foregroundColor()
    }

    override fun setForegroundColor(foregroundColor: TileColor) {
        backend.setForegroundColor(foregroundColor)
    }

    override fun enableModifiers(modifiers: Set<Modifier>) {
        backend.enableModifiers(modifiers)
    }

    override fun enableModifiers(vararg modifiers: Modifier) {
        enableModifiers(modifiers.toSet())
    }

    override fun disableModifiers(modifiers: Set<Modifier>) {
        backend.disableModifiers(modifiers)
    }

    override fun disableModifiers(vararg modifiers: Modifier) {
        disableModifiers(modifiers.toSet())
    }

    override fun setModifiers(modifiers: Set<Modifier>) {
        backend.setModifiers(modifiers)
    }

    override fun clearModifiers() {
        backend.clearModifiers()
    }

    override fun resetColorsAndModifiers() {
        backend.resetColorsAndModifiers()
    }

    override fun activeModifiers(): Set<Modifier> {
        return backend.activeModifiers()
    }

    override fun setStyleFrom(source: StyleSet) {
        backend.setStyleFrom(source)
    }

    override fun useContentsOf(tileGrid: InternalTileGrid) {
        backend = tileGrid.backend
        layerable = tileGrid.layerable
        animationHandler = tileGrid.animationHandler
    }

    override fun reset() {
        backend = originalBackend
        layerable = originalLayerable
        animationHandler = originalAnimationHandler
    }

    override fun close() {
        animationHandler.close()
    }

    override fun clear() {
        backend.clear()
        layerable = DefaultLayerable(backend.size())
    }

    private fun moveCursorToNextLine() {
        putCursorAt(cursorPosition().withRelativeY(1).withX(0))
    }


}
