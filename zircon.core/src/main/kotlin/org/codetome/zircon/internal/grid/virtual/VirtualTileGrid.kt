package org.codetome.zircon.internal.grid.virtual

import org.codetome.zircon.api.behavior.Boundable
import org.codetome.zircon.api.behavior.Drawable
import org.codetome.zircon.api.behavior.FontOverride
import org.codetome.zircon.api.builder.data.TileBuilder
import org.codetome.zircon.api.builder.graphics.TileImageBuilder
import org.codetome.zircon.api.data.Cell
import org.codetome.zircon.api.data.Position
import org.codetome.zircon.api.data.Size
import org.codetome.zircon.api.data.Tile
import org.codetome.zircon.api.graphics.TileImage
import org.codetome.zircon.api.input.Input
import org.codetome.zircon.api.input.KeyStroke
import org.codetome.zircon.api.tileset.Tileset
import org.codetome.zircon.api.util.Consumer
import org.codetome.zircon.api.util.TextUtils
import org.codetome.zircon.internal.behavior.InternalCursorHandler
import org.codetome.zircon.internal.behavior.InternalLayerable
import org.codetome.zircon.internal.behavior.ShutdownHook
import org.codetome.zircon.internal.behavior.impl.DefaultCursorHandler
import org.codetome.zircon.internal.behavior.impl.DefaultFontOverride
import org.codetome.zircon.internal.behavior.impl.DefaultLayerable
import org.codetome.zircon.internal.behavior.impl.DefaultShutdownHook
import org.codetome.zircon.internal.event.Event
import org.codetome.zircon.internal.event.EventBus
import org.codetome.zircon.internal.grid.AbstractTileGrid
import org.codetome.zircon.internal.grid.InternalTileGrid

class VirtualTileGrid(initialSize: Size = Size.defaultTerminalSize(),
                      initialTileset: Tileset,
                      private val fontOverride: FontOverride = DefaultFontOverride(
                              initialTileset = initialTileset),
                      private val cursorHandler: InternalCursorHandler = DefaultCursorHandler(
                              cursorSpace = initialSize),
                      private val layerable: InternalLayerable = DefaultLayerable(
                              size = initialSize,
                              supportedFontSize = initialTileset.getSize()),
                      private val shutdownHook: ShutdownHook = DefaultShutdownHook())
    : AbstractTileGrid(), InternalTileGrid,
        InternalCursorHandler by cursorHandler,
        InternalLayerable by layerable,
        FontOverride by fontOverride,
        ShutdownHook by shutdownHook {

    private var terminalSize = initialSize
    private var backend: TileImage = createBackend(terminalSize)


    override fun drainDirtyPositions() =
            cursorHandler.drainDirtyPositions().plus(layerable.drainDirtyPositions()).also { dirtyPositions ->
                val blinkingChars = dirtyPositions.filter {
                    getTileAt(it).let { char ->
                        char.isPresent && char.get().isBlinking() // TODO: check layers too
                    }
                }
                blinkingChars.forEach {
                    setPositionDirty(it)
                }
            }

    override fun isDirty() = cursorHandler.isDirty().or(layerable.isDirty())

    override fun setPositionDirty(position: Position) {
        layerable.setPositionDirty(position)
    }

    override fun draw(drawable: Drawable, offset: Position) {
        drawable.drawOnto(this, offset)
        // TODO: fix this
        if (drawable is Boundable) {
            drawable.getBoundableSize().fetchPositions().forEach {
                setPositionDirty(it + offset)
            }
        } else {
            setPositionDirty(offset)
        }
    }

    override fun createSnapshot(): List<Cell> {
        return backend.createSnapshot()
    }

    override fun getBoundableSize(): Size = terminalSize

    override fun setSize(newSize: Size) {
        if (newSize != terminalSize) {
            this.terminalSize = newSize
            backend = backend.resize(newSize, Tile.defaultTile())
            resizeCursorSpace(newSize)
            // TODO: this can be optimized later
            terminalSize.fetchPositions().forEach {
                setPositionDirty(it)
            }
            super.onResized(newSize)
        }
    }

    override fun onInput(listener: Consumer<Input>) {
        EventBus.subscribe<Event.Input> { (input) ->
            listener.accept(input)
        }
    }

    override fun clear() {
        backend = createBackend(terminalSize)
        terminalSize.fetchPositions().forEach {
            setPositionDirty(it)
        }
        putCursorAt(Position.defaultPosition())
    }

    override fun putCharacter(c: Char) {
        putTextCharacter(TileBuilder.newBuilder()
                .character(c)
                .foregroundColor(getForegroundColor())
                .backgroundColor(getBackgroundColor())
                .modifiers(getActiveModifiers())
                .build())
    }

    override fun putTextCharacter(tc: Tile) {
        if (tc.getCharacter() == '\n') {
            moveCursorToNextLine()
        } else if (TextUtils.isPrintableCharacter(tc.getCharacter())) {
            backend.setTileAt(getCursorPosition(), tc)
            setPositionDirty(getCursorPosition())
            moveCursorForward()
        }
    }

    override fun flush() {

    }

    override fun close() {
        EventBus.broadcast(Event.Input(KeyStroke.EOF_STROKE))
    }

    override fun getTileAt(position: Position) =
            backend.getTileAt(position)

    override fun setCharAt(position: Position, char: Char) {
        setTileAt(position, TileBuilder.newBuilder()
                .character(char)
                .styleSet(toStyleSet())
                .build())
    }


    override fun setTileAt(position: Position, tile: Tile) {
        if (containsPosition(position)) {
            backend.setTileAt(position, tile)
            setPositionDirty(position)
        }
    }

    override fun forEachDirtyCell(fn: (Cell) -> Unit) {
        val dirtyPositions = drainDirtyPositions()
        dirtyPositions.forEach { pos ->
            val char = backend.getTileAt(pos)
            if (char.isPresent) {
                fn(Cell(pos, char.get()))
            }
        }
    }

    override fun forEachCell(fn: (Cell) -> Unit) {
        backend.fetchCells().forEach(fn)
    }

    private fun moveCursorToNextLine() {
        putCursorAt(getCursorPosition().withRelativeY(1).withX(0))
    }

    private fun createBackend(initialSize: Size) =
            TileImageBuilder.newBuilder()
                    .size(initialSize)
                    .build()

}
