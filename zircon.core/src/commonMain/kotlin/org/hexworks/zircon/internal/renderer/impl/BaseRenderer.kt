package org.hexworks.zircon.internal.renderer.impl

import org.hexworks.zircon.internal.extensions.asInternal
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.internal.util.nowMillis
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.extensions.fetchPositions
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.extensions.isBlinking
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.extensions.isOpaque
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.graphics.forEachVisibleTile
import org.hexworks.zircon.internal.graphics.tilesetOrElse
import org.hexworks.zircon.internal.graphics.toRenderLayers
import org.hexworks.zircon.internal.util.reportError
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.renderer.RenderData
import org.hexworks.zircon.internal.renderer.Renderer

public abstract class BaseRenderer<C : Any, V : Any>(
    tileGrid: TileGrid,
    private val tilesetLoader: TilesetLoader<C>
) : Renderer<C, V> {

    protected val tileGrid: InternalTileGrid = tileGrid.asInternal()

    private val config = this.tileGrid.config
    private val gridPositions = tileGrid.size.fetchPositions().toList()
    private val isClosed = false.toProperty()

    private val beforeRenderDataProp = RenderData(nowMillis()).toProperty()
    private val afterRenderDataProp = RenderData(nowMillis()).toProperty()

    private var blinkOn = true
    private var lastBlink: Long = nowMillis()
    private var lastRender: Long = lastBlink

    /**
     * Whether the grid's cursor is drawn into the tiles (the topmost tile of its cell with
     * the colours swapped). A renderer with a cursor of its own, like a terminal, turns it off.
     */
    protected open val drawsCursor: Boolean = true

    protected var beforeRenderData: RenderData by beforeRenderDataProp.asDelegate()
    protected var afterRenderData: RenderData by afterRenderDataProp.asDelegate()

    override val closedProperty: ObservableValue<Boolean>
        get() = isClosed

    final override fun close() {
        isClosed.value = true
        doClose()
    }

    final override fun beforeRender(listener: (RenderData) -> Unit): Subscription = beforeRenderDataProp.onChange {
        listener(it.newValue)
    }

    final override fun afterRender(listener: (RenderData) -> Unit): Subscription = afterRenderDataProp.onChange {
        listener(it.newValue)
    }

    /**
     * Template method that is responsible for processing all input events for the given frame.
     */
    protected abstract fun processInputEvents()

    /**
     * Template method that is called during [render] but before the actual rendering happens.
     * Can be used to configure the [context] or other components for rendering.
     */
    protected open fun prepareRender(context: C) {}

    /**
     * Template method that implments the close mechanism.
     */
    protected open fun doClose() {
    }

    final override fun render(context: C) {
        if (!closed) {
            val now = nowMillis()
            beforeRenderDataProp.value = RenderData(now)
            processInputEvents()
            tileGrid.updateAnimations(now, tileGrid)
            handleBlink(now)
            prepareRender(context)
            renderAllTiles(context)
            lastRender = now
            afterRenderDataProp.value = RenderData(nowMillis())
        }
    }

    // TODO: use a drawing strategy here
    // Scratch space for one cell's visible tiles, top first; reused across cells and frames.
    private val cellTiles = ArrayList<Tile>(8)
    private val cellTilesets = ArrayList<TilesetResource>(8)

    private fun renderAllTiles(context: C) {
        val layers = tileGrid.renderables.toRenderLayers()
        val cursorAt = if (shouldDrawCursor()) tileGrid.cursorPosition else null
        for (pos in gridPositions) {
            // Tiles are collected top first (stopping at the first opaque one) and drawn bottom first.
            layers.forEachVisibleTile(pos) { tile, layerTileset -> push(tile, layerTileset) }
            if (pos == cursorAt) drawCursorOnCell()
            for (j in cellTiles.size - 1 downTo 0) {
                // A tile (or tileset) that fails to draw leaves its cell; the frame goes on.
                try {
                    renderTile(
                        context = context,
                        position = pos,
                        tile = cellTiles[j],
                        tileset = tilesetLoader.loadTilesetFrom(cellTilesets[j])
                    )
                } catch (e: Exception) {
                    reportError("Tile at $pos", e)
                }
            }
            cellTiles.clear()
            cellTilesets.clear()
        }
    }

    /** Adds [tile] to the current cell; `true` when it is opaque, so collection can stop. */
    private fun push(tile: Tile, layerTileset: TilesetResource): Boolean {
        cellTiles.add(tile)
        cellTilesets.add(tile.tilesetOrElse(layerTileset))
        return tile.isOpaque
    }

    /**
     * The cursor is the topmost tile of its cell with the colours swapped; an empty cell
     * gets a block in the default foreground colour, drawn with the grid's tileset.
     */
    private fun drawCursorOnCell() {
        val top = cellTiles.firstOrNull()
        if (top is CharacterTile) {
            val foreground = top.foregroundColor.takeUnless { it.isTransparent } ?: Color.DEFAULT_FOREGROUND_COLOR
            val background = top.backgroundColor.takeUnless { it.isTransparent } ?: Color.DEFAULT_BACKGROUND_COLOR
            cellTiles[0] = top.withBackgroundColor(foreground).withForegroundColor(background)
        } else {
            cellTiles.add(0, CURSOR_TILE)
            cellTilesets.add(0, tileGrid.tileset)
        }
    }

    private fun shouldDrawCursor(): Boolean {
        return drawsCursor && tileGrid.isCursorVisible &&
                (config.isCursorBlinking.not() || config.isCursorBlinking && blinkOn)
    }

    private fun renderTile(
        context: C,
        position: Position,
        tile: Tile,
        tileset: Tileset<C>
    ) {
        if (tile.isNotEmpty) {
            var tempTile = tile
            // Modifiers are rare; the checks below cost a set lookup and a list per tile,
            // so they only run for tiles that have any.
            if (tempTile is CharacterTile && tempTile.modifiers.isNotEmpty()) {
                if (tempTile.isBlinking && blinkOn) {
                    return
                }
                //! TODO: add `List<Modifier>`s to all `Tile`s
                for (modifier in tempTile.modifiers) {
                    if (modifier is TileModifier<*>) {
                        @Suppress("UNCHECKED_CAST")
                        val tileModifier = modifier as TileModifier<Tile>
                        if (tileModifier.canTransform(tempTile)) {
                            tempTile = tileModifier.transform(tempTile)
                        }
                    }
                }
            }
            // The tileset was resolved from the original tile; a modifier may have swapped it.
            val finalTileset = if (tempTile !== tile && tempTile is TilesetHolder && tempTile.tileset.isNotUnknown) {
                tilesetLoader.loadTilesetFrom(tempTile.tileset)
            } else tileset
            finalTileset.drawTile(
                tile = tempTile,
                context = context,
                position = position
            )
        }
    }

    private fun handleBlink(now: Long) {
        if (now > lastBlink + config.blinkLengthInMilliSeconds) {
            blinkOn = !blinkOn
            lastBlink = now
        }
    }

    private companion object {
        val CURSOR_TILE: Tile = Tile.empty().withBackgroundColor(Color.DEFAULT_FOREGROUND_COLOR)
    }

}