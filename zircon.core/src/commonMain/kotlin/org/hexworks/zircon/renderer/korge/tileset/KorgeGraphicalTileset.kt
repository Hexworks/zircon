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
import korlibs.io.file.fullName
import korlibs.io.file.std.openAsZip
import korlibs.math.geom.slice.splitInRows
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.GraphicalTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.*
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.renderer.korge.tileset.LoadingState.*
import org.hexworks.zircon.renderer.korge.toRGBA
import kotlin.reflect.KClass

class KorgeGraphicalTileset(
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
    private var tileLookup = mutableMapOf<String, BmpSlice>()

    private val bgSlice = atlas.add(Bitmap32(width, height) { _, _ -> Colors.WHITE }).slice


    init {
        require(resource.tileType == TileType.GRAPHICAL_TILE) {
            "Graphical tilesets only support ${TileType.GRAPHICAL_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
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
            var finalTile: GraphicalTile = tile
            finalTile.modifiers.filterIsInstance<TileModifier<GraphicalTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    finalTile = modifier.transform(finalTile)
                }
            }

            val px = position.x * widthF
            val py = position.y * heightF

            val bg = tile.backgroundColor.toRGBA()

            val fgSlice = tileLookup[tile.name]

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
                color = Colors.TRANSPARENT,
                context = context
            )
        }
    }


    private suspend fun preload() {
        println("KorgeTileset.preload: ${resource.resourceType}, ${resource.tilesetType}, '${resource.path}'")
        try {
            val vfsFile = loadResource(resource)
            if (vfsFile.isDirectory()) {
                error("Unexpected directory: ${vfsFile.fullName} for graphical tileset (expected zip file)")
            } else {
                val zip = vfsFile.openAsZip()
                val info = zip.loadFile("tileinfo.yml").readString().asYaml()
                val tilesetName = info["name"].str
                val tileSize = info["size"].int
                val files = info["files"].list
                for (file in files) {
                    val fileName = file["name"].str
                    val tilesPerRow = file["tilesPerRow"].int
                    val tiles = file["tiles"].list
                    val tileNames = tiles.map { it["name"].str }
                    val bitmap = zip.loadFile(fileName).readBitmap().toBMP32IfRequired()
                    val tileBitmaps = bitmap.slice().splitInRows(tilesPerRow, tileSize)
                    this.tileLookup.putAll(tileNames.zip(tileBitmaps).associate { it.first to it.second })
                }
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