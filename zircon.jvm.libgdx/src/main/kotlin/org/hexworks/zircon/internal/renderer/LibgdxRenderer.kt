package org.hexworks.zircon.internal.renderer

import com.badlogic.gdx.Gdx
import com.badlogic.gdx.graphics.Color
import com.badlogic.gdx.graphics.OrthographicCamera
import com.badlogic.gdx.graphics.Pixmap
import com.badlogic.gdx.graphics.Texture
import com.badlogic.gdx.graphics.g2d.Sprite
import com.badlogic.gdx.graphics.g2d.SpriteBatch
import com.badlogic.gdx.graphics.glutils.ShapeRenderer
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.LibgdxApplications
import org.hexworks.zircon.api.application.CursorStyle
import org.hexworks.zircon.api.application.filterByType
import org.hexworks.zircon.api.behavior.TilesetHolder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.api.tileset.TilesetLoader
import org.hexworks.zircon.internal.RunTimeStats
import org.hexworks.zircon.internal.config.RuntimeConfig
import org.hexworks.zircon.internal.data.PixelPosition
import org.hexworks.zircon.internal.graphics.FastTileGraphics
import org.hexworks.zircon.internal.grid.InternalTileGrid
import org.hexworks.zircon.internal.tileset.impl.DefaultTilesetLoader


@Suppress("UNCHECKED_CAST", "UNUSED_PARAMETER")
class LibgdxRenderer(
    private val grid: InternalTileGrid,
    private val debug: Boolean = false
) : Renderer {

    override val closedValue: Property<Boolean> = false.toProperty()

    private val config = RuntimeConfig.config
    private var maybeBatch: SpriteBatch? = null
    private lateinit var cursorRenderer: ShapeRenderer
    private val tilesetLoader: TilesetLoader<SpriteBatch> = DefaultTilesetLoader(
        LibgdxApplications.DEFAULT_FACTORIES + config.tilesetLoaders.filterByType(SpriteBatch::class)
    )
    private var blinkOn = true
    private var timeSinceLastBlink: Float = 0f

    private lateinit var backgroundTexture: Texture
    private var backgroundWidth: Int = 0
    private var backgroundHeight: Int = 0

    override fun create() {
        maybeBatch = SpriteBatch().apply {
            val camera = OrthographicCamera()
            camera.setToOrtho(true)
            projectionMatrix = camera.combined
        }
        cursorRenderer = ShapeRenderer()
        val whitePixmap = Pixmap(grid.widthInPixels, grid.heightInPixels, Pixmap.Format.RGBA8888)
        whitePixmap.setColor(Color.WHITE)
        whitePixmap.fill()
        backgroundTexture = Texture(whitePixmap)
        backgroundWidth = whitePixmap.width / grid.width
        backgroundHeight = whitePixmap.height / grid.height
        whitePixmap.dispose()
    }

    override fun render() {
        if (debug) {
            RunTimeStats.addTimedStatFor("debug.render.time") {
                doRender(Gdx.app.graphics.deltaTime)
            }
        } else doRender(Gdx.app.graphics.deltaTime)
    }

    override fun close() {
        closedValue.value = true
        maybeBatch?.let(SpriteBatch::dispose)
    }

    private fun doRender(delta: Float) {
        handleBlink(delta)

        maybeBatch?.let { batch ->
            batch.begin()
            val tilesToRender = mutableMapOf<Position, MutableList<Pair<Tile, TilesetResource>>>()
            val renderables = grid.renderables
            for (i in renderables.indices) {
                val renderable = renderables[i]
                if (!renderable.isHidden) {
                    val graphics = FastTileGraphics(
                        initialSize = renderable.size,
                        initialTileset = renderable.tileset,
                        initialTiles = emptyMap()
                    )
                    renderable.render(graphics)
                    graphics.contents().forEach { (tilePos, tile) ->
                        val finalPos = tilePos + renderable.position
                        tilesToRender.getOrPut(finalPos) { mutableListOf() }
                        if (tile.isOpaque) {
                            tilesToRender[finalPos] = mutableListOf(tile to renderable.tileset)
                        } else {
                            tilesToRender[finalPos]?.add(tile to renderable.tileset)
                        }
                    }
                }
            }
            for ((pos, tiles) in tilesToRender) {
                for ((tile, tileset) in tiles) {
                    renderTile(
                        batch = batch,
                        position = pos.toPixelPosition(tileset),
                        tile = tile,
                        tileset = tilesetLoader.loadTilesetFrom(tileset)
                    )
                }
            }
            batch.end()
            cursorRenderer.projectionMatrix = batch.projectionMatrix
            if (shouldDrawCursor()) {
                grid.getTileAt(grid.cursorPosition).map { it ->
                    drawCursor(cursorRenderer, it, grid.cursorPosition)
                }
            }
        }
    }

    private fun renderTile(
        batch: SpriteBatch,
        tile: Tile,
        tileset: Tileset<SpriteBatch>,
        position: PixelPosition
    ) {
        if (tile.isNotEmpty) {
            var finalTile = tile
            finalTile.modifiers.filterIsInstance<TileTransformModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    (finalTile as? CharacterTile)?.let {
                        finalTile = modifier.transform(it)
                    }
                }
            }
            finalTile = if (tile.isBlinking && blinkOn) {
                tile.withBackgroundColor(tile.foregroundColor)
                    .withForegroundColor(tile.backgroundColor)
            } else {
                tile
            }
            drawBack(
                tile = finalTile,
                surface = batch,
                position = position
            )
            ((finalTile as? TilesetHolder)?.let {
                tilesetLoader.loadTilesetFrom(it.tileset)
            } ?: tileset).drawTile(
                tile = finalTile,
                surface = batch,
                position = position
            )
        }
    }

    private fun drawBack(
        tile: Tile,
        surface: SpriteBatch,
        position: Position
    ) {
        val x = position.x.toFloat()
        val y = position.y.toFloat()
        val backSprite = Sprite(backgroundTexture)
        backSprite.setSize(backgroundWidth.toFloat(), backgroundHeight.toFloat())
        backSprite.setOrigin(0f, 0f)
        backSprite.setOriginBasedPosition(x, y)
        backSprite.flip(false, true)
        backSprite.color = Color(
            tile.backgroundColor.red.toFloat() / 255,
            tile.backgroundColor.green.toFloat() / 255,
            tile.backgroundColor.blue.toFloat() / 255,
            tile.backgroundColor.alpha.toFloat() / 255
        )
        backSprite.draw(surface)
    }

    private fun handleBlink(delta: Float) {
        timeSinceLastBlink += delta
        if (timeSinceLastBlink > config.blinkLengthInMilliSeconds) {
            blinkOn = !blinkOn
        }
    }

    private fun drawCursor(shapeRenderer: ShapeRenderer, character: Tile, position: Position) {
        val tileWidth = grid.tileset.width
        val tileHeight = grid.tileset.height
        val x = (position.x * tileWidth).toFloat()
        val y = (position.y * tileHeight).toFloat()
        val cursorColor = colorToGDXColor(config.cursorColor)
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled)
        shapeRenderer.color = cursorColor
        when (config.cursorStyle) {
            CursorStyle.USE_CHARACTER_FOREGROUND -> {
                if (blinkOn) {
                    shapeRenderer.color = colorToGDXColor(character.foregroundColor)
                    shapeRenderer.rect(x, y, tileWidth.toFloat(), tileHeight.toFloat())
                }
            }
            CursorStyle.FIXED_BACKGROUND -> shapeRenderer.rect(x, y, tileWidth.toFloat(), tileHeight.toFloat())
            CursorStyle.UNDER_BAR -> shapeRenderer.rect(x, y + tileHeight - 3, tileWidth.toFloat(), 2.0f)
            CursorStyle.VERTICAL_BAR -> shapeRenderer.rect(x, y + 1, 2.0f, tileHeight - 2.0f)
        }
        shapeRenderer.end()
    }

    private fun shouldDrawCursor(): Boolean {
        return grid.isCursorVisible &&
                (config.isCursorBlinking.not() || config.isCursorBlinking && blinkOn)
    }

    private fun colorToGDXColor(color: TileColor): Color {
        return Color(
            color.red / 255.0f,
            color.green / 255.0f,
            color.blue / 255.0f,
            color.alpha / 255.0f
        )
    }

    fun TileColor.toGdxColor(): Color {
        return Color(
            this.red / 255.0f,
            this.green / 255.0f,
            this.blue / 255.0f,
            this.alpha / 255.0f
        )
    }
}
