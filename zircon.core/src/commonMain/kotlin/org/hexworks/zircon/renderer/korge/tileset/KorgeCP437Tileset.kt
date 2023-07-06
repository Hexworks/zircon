package org.hexworks.zircon.renderer.korge.tileset

import korlibs.image.atlas.MutableAtlasUnit
import korlibs.image.atlas.add
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.BmpSlice
import korlibs.image.bitmap.slice
import korlibs.image.color.Colors
import korlibs.image.color.RGBA
import korlibs.image.format.readBitmap
import korlibs.io.async.launchImmediately
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.resourcesVfs
import korlibs.math.geom.slice.splitInRows
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileTransformModifier
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.resource.TilesetSourceType
import org.hexworks.zircon.internal.util.CP437Utils
import org.hexworks.zircon.renderer.korge.tileset.LoadingState.*
import org.hexworks.zircon.renderer.korge.toRGBA
import kotlin.reflect.KClass

class KorgeCP437Tileset(
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

    private var loadingState = NOT_LOADED
    private val atlas = MutableAtlasUnit()
    private var tiles: List<BmpSlice> = listOf()

    private val bgSlice = atlas.add(Bitmap32(width, height) { _, _ -> Colors.WHITE }).slice


    init {
        require(resource.tileType == TileType.CHARACTER_TILE) {
            "CP437 tilesets only support ${TileType.CHARACTER_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: KorgeContext, position: Position) {
        if (loadingState == NOT_LOADED) {
            loadingState = LOADING
            launchImmediately(context.context.coroutineContext) {
                preload()
                loadingState = LOADED
            }
        }
        if (loadingState == LOADED) {
            require(tile is CharacterTile) {
                "A CP437 renderer can only render ${CharacterTile::class.simpleName}s. Offending tile: $tile."
            }
            var finalTile: CharacterTile = tile
            finalTile.modifiers.filterIsInstance<TileTransformModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    finalTile = modifier.transform(finalTile)
                }
            }
            val idx = CP437Utils.fetchCP437IndexForChar(finalTile.character)
            val fgSlice = tiles[idx]

            val px = position.x * widthF
            val py = position.y * heightF

            val fg = tile.foregroundColor.toRGBA();
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

            // TODO: modifier support
//            val strikeoutTile = atlas.add(Bitmap32(tileWidth, tileHeight).context2d {
//                stroke(Colors.WHITE, 2f) { line(Point(0, tileHeight * .5f), Point(tileWidth, tileHeight * .5f)) }
//            }).slice
//            val underlinedTile = atlas.add(Bitmap32(tileWidth, tileHeight).context2d {
//                stroke(Colors.WHITE, 2f) { line(Point(0, tileHeight - 1), Point(tileWidth, tileHeight - 1)) }
//            }).slice
//            if (tile.isCrossedOut) drawSlice(
//                px, py, strikeoutTile, tile.foregroundColor.toRGBA(), false, false
//            )
//            if (tile.isUnderlined) drawSlice(
//                px, py, underlinedTile, tile.foregroundColor.toRGBA(), false, false
//            )
        }
    }


    private suspend fun preload() {
        println("KorgeTileset.preload: ${resource.tilesetSourceType}, ${resource.tilesetType}, '${resource.path}'")
        try {
            val vfs = when (resource.tilesetSourceType) {
                TilesetSourceType.FILESYSTEM -> localCurrentDirVfs
                TilesetSourceType.JAR -> resourcesVfs
            }
            val vfsFile = vfs[resource.path]

            if (!vfsFile.exists()) {
                error("File $vfsFile doesn't exist!")
            }
            val bitmap = vfsFile.readBitmap().toBMP32IfRequired()
            tiles = bitmap.slice().splitInRows(resource.width, resource.width)

            println("!! READY: $this : $resource : ${resource.tileType} : ${resource.tilesetType}")
        } catch (e: Throwable) {
            e.printStackTrace()
        }
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