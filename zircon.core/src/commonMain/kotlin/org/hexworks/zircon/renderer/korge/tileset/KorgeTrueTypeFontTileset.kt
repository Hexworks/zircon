package org.hexworks.zircon.renderer.korge.tileset

import korlibs.image.atlas.MutableAtlasUnit
import korlibs.image.atlas.add
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.BmpSlice
import korlibs.image.bitmap.BmpSlice32
import korlibs.image.color.Colors
import korlibs.image.color.RGBA
import korlibs.image.font.TtfFont
import korlibs.image.font.readTtfFont
import korlibs.image.font.renderGlyphToBitmap
import korlibs.io.async.launchImmediately
import korlibs.io.file.VfsFile
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.loadResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.renderer.korge.tileset.LoadingState.LOADED
import org.hexworks.zircon.renderer.korge.toRGBA
import kotlin.reflect.KClass

class KorgeTrueTypeFontTileset(
    private val resource: TilesetResource
) : Tileset<KorgeContext> {

    override val id: UUID = UUID.randomUUID()
    override val width: Int
        get() = resource.width
    override val height: Int
        get() = resource.height
    override val targetType: KClass<KorgeContext> = KorgeContext::class

    private val widthF = width.toFloat()
    private val heightF = height.toFloat()

    private var atlas: MutableAtlasUnit = MutableAtlasUnit()
    private var tileLookup: MutableMap<Int, BmpSlice> = mutableMapOf()

    private val bgSlice = atlas.add(Bitmap32(width, height) { _, _ -> Colors.WHITE }).slice

    private var loadingState = LoadingState.NOT_LOADED

    lateinit var vfsFile: VfsFile
    lateinit var font: TtfFont

    init {
        require(resource.tileType == TileType.CHARACTER_TILE) {
            "True Type Font tilesets only support ${TileType.CHARACTER_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: KorgeContext, position: Position) {
        if (loadingState == LoadingState.NOT_LOADED) {
            loadingState = LoadingState.LOADING
            launchImmediately(context.context.coroutineContext) {
                preload()
            }
        }
        if (loadingState == LOADED) {
            require(tile is CharacterTile) {
                "A true type font renderer can only render ${CharacterTile::class.simpleName}s. Offending tile: $tile."
            }
            var finalTile: CharacterTile = tile
            finalTile.modifiers.filterIsInstance<TileModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    finalTile = modifier.transform(finalTile)
                }
            }
            val fgSlice = tileLookup.getOrPut(tile.character.code) { loadTile(tile.character) }

            val px = position.x * widthF
            val py = position.y * heightF

            val fg = tile.foregroundColor.toRGBA()
            val bg = tile.backgroundColor.toRGBA()

            drawSlice(
                x = px,
                y = py,
                slice = bgSlice,
                color = bg,
                context = context
            )
            drawSlice(
                x = px,
                y = py,
                slice = fgSlice,
                color = fg,
                context = context
            )
        }
    }

    private suspend fun preload() {
        try {
            vfsFile = loadResource(resource)
            font = vfsFile.readTtfFont()
            loadingState = LOADED
        } catch (e: Throwable) {
            e.printStackTrace()
        }
    }

    private fun loadTile(char: Char): BmpSlice32 {
        println("Trying to load $char")
        return atlas.add(
            font.renderGlyphToBitmap(
                heightF.toDouble(),
                char.code,
                Colors.WHITE
            ).bmp.toBMP32()
        ).slice
    }

    private fun drawSlice(
        x: Float,
        y: Float,
        slice: BmpSlice?,
        color: RGBA,
        context: KorgeContext
    ) {
        val (ctx, batch, view) = context
        if (slice == null) return
        batch.drawQuad(
            ctx.getTex(slice),
            x,
            y,
            widthF,
            heightF,
            colorMul = color,
            m = view.globalMatrix,
            blendMode = view.renderBlendMode
        )
    }
}