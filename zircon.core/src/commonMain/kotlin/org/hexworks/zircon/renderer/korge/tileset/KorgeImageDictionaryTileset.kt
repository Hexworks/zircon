package org.hexworks.zircon.renderer.korge.tileset

import korlibs.image.atlas.MutableAtlasUnit
import korlibs.image.atlas.add
import korlibs.image.bitmap.BmpSlice
import korlibs.image.color.Colors
import korlibs.image.color.RGBA
import korlibs.image.format.readBitmap
import korlibs.io.async.launchImmediately
import korlibs.io.file.baseName
import korlibs.io.file.extensionLC
import korlibs.io.file.fullName
import korlibs.math.geom.Vector2
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.renderer.korge.tileset.LoadingState.*
import kotlin.reflect.KClass

class KorgeImageDictionaryTileset(
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

    private var atlas: MutableAtlasUnit = MutableAtlasUnit()
    private var tileLookup = mapOf<String, BmpSlice>()

    init {
        require(resource.tileType == TileType.CHARACTER_TILE) {
            "CP437 tilesets only support ${TileType.CHARACTER_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: KorgeContext, position: Position) {
        require(tile is GraphicalTile) {
            "A graphical tile renderer can only render ${GraphicalTile::class.simpleName}s. Offending tile: $tile."
        }
        if (loadingState == NOT_LOADED) {
            loadingState = LOADING
            launchImmediately(context.context.coroutineContext) {
                preload()
                loadingState = LOADED
            }
        }
        if (loadingState == LOADED) {
            val px = position.x * widthF
            val py = position.y * heightF

            val slice = tileLookup[tile.name]

            drawSlice(
                x = px,
                y = py,
                slice = slice,
                color = Colors.TRANSPARENT,
                context = context
            )
        }
    }


    private suspend fun preload() {
        println("KorgeTileset.preload: ${resource.tilesetSourceType}, ${resource.tilesetType}, '${resource.path}'")
        try {
            val vfsFile = loadVfs(resource)
            if (vfsFile.isDirectory()) {
                val tiles = mutableListOf<BmpSlice>()
                val lookup = mutableMapOf<String, BmpSlice>()
                for (imageFile in vfsFile.listSimple().filter { it.extensionLC == "png" }) {
                    val tile = atlas.add(imageFile.readBitmap().toBMP32IfRequired()).slice
                    tiles += tile
                    lookup[imageFile.baseName] = tile
                }
                this.tileLookup = lookup
            } else {
                error("Unexpected file: ${vfsFile.fullName} for image dictionary tileset (expected directory)")
            }
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