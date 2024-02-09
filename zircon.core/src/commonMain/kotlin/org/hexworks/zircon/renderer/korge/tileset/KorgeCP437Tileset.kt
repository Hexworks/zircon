package org.hexworks.zircon.renderer.korge.tileset

import korlibs.image.atlas.MutableAtlasUnit
import korlibs.image.atlas.add
import korlibs.image.bitmap.Bitmap32
import korlibs.image.bitmap.BmpSlice
import korlibs.image.bitmap.context2d
import korlibs.image.bitmap.slice
import korlibs.image.color.Colors
import korlibs.image.color.RGBA
import korlibs.image.format.readBitmap
import korlibs.image.vector.Context2d
import korlibs.io.async.launchImmediately
import korlibs.io.file.std.localCurrentDirVfs
import korlibs.io.file.std.resourcesVfs
import korlibs.math.geom.Point
import korlibs.math.geom.slice.splitInRows
import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.hasBorder
import org.hexworks.zircon.api.extensions.isCrossedOut
import org.hexworks.zircon.api.extensions.isUnderlined
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderType
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.resource.TileType
import org.hexworks.zircon.internal.util.CP437Index
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
            finalTile.modifiers.filterIsInstance<TileModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    finalTile = modifier.transform(finalTile)
                }
            }
            val idx = finalTile.character.CP437Index()
            val fgSlice = tiles[idx]

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

            // TODO: modifier support

            if (tile.isCrossedOut) {
                val strikeoutTile = atlas.add(Bitmap32(width, height).context2d {
                    stroke(Colors.WHITE, 2f) {
                        line(
                            Point(0, heightF * .5f),
                            Point(widthF, heightF * .5f)
                        )
                    }
                }).slice

                drawSlice(
                    px, py, strikeoutTile, tile.foregroundColor.toRGBA(), context,
                )
            }
            if (tile.isUnderlined) {
                val underlinedTile = atlas.add(Bitmap32(width, height).context2d {
                    stroke(Colors.WHITE, 2f) {
                        line(
                            Point(0, heightF - 1),
                            Point(widthF, heightF - 1)
                        )
                    }
                }).slice
                drawSlice(
                    px, py, underlinedTile, tile.foregroundColor.toRGBA(), context
                )
            }
if (tile.hasBorder) {
    tile.fetchBorderData().forEach { border ->
        val (_, color, borderSize, positions) = border
        val thickness = borderSize.toFloat()

        positions.forEach { pos ->
            val borderTile = when (pos) {
                BorderPosition.TOP -> {
                    atlas.add(Bitmap32(width, height).context2d {
                        stroke(Colors.WHITE, thickness) {
                            line(
                                Point(0, 0),
                                Point(widthF, 0)
                            )
                        }
                    }).slice
                }

                BorderPosition.RIGHT -> {
                    atlas.add(Bitmap32(width, height).context2d {
                        stroke(Colors.WHITE, thickness) {
                            line(
                                Point(widthF - thickness, 0),
                                Point(widthF - thickness, heightF)
                            )
                        }
                    }).slice
                }

                BorderPosition.BOTTOM -> {
                    atlas.add(Bitmap32(width, height).context2d {
                        stroke(Colors.WHITE, thickness) {
                            line(
                                Point(0, heightF - thickness),
                                Point(widthF, heightF - thickness)
                            )
                        }
                    }).slice
                }

                BorderPosition.LEFT -> {
                    atlas.add(Bitmap32(width, height).context2d {
                        stroke(Colors.WHITE, thickness) {
                            line(
                                Point(0, 0),
                                Point(0, heightF)
                            )
                        }
                    }).slice
                }
            }
            drawSlice(
                px, py, borderTile, color.toRGBA(), context
            )
        }
    }
}
        }
    }


    private suspend fun preload() {
        println("KorgeTileset.preload: ${resource.resourceType}, ${resource.tilesetType}, '${resource.path}'")
        try {
            val vfs = when (resource.resourceType) {
                ResourceType.FILESYSTEM -> localCurrentDirVfs
                ResourceType.PROJECT -> resourcesVfs
            }
            val vfsFile = vfs[resource.path]

            if (!vfsFile.exists()) {
                error("File $vfsFile doesn't exist!")
            }
            val bitmap = vfsFile.readBitmap().toBMP32IfRequired()
            tiles = bitmap.slice().splitInRows(resource.width, resource.width)
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