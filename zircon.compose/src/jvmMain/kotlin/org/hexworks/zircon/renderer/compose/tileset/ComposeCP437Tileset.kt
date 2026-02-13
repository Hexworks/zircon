package org.hexworks.zircon.renderer.compose.tileset

import org.hexworks.cobalt.core.api.UUID
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.data.TileType.CHARACTER_TILE
import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.modifier.TileModifier
import org.hexworks.zircon.api.resource.ResourceType
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.tileset.Tileset
import org.hexworks.zircon.internal.util.CP437Index
import org.hexworks.zircon.renderer.compose.ComposeContext
import org.jetbrains.skia.Bitmap
import org.jetbrains.skia.ColorAlphaType
import org.jetbrains.skia.Image
import org.jetbrains.skia.ImageInfo
import java.io.File
import kotlin.reflect.KClass

/**
 * CP437 character tileset implementation for Compose.
 *
 * Loads a CP437 sprite sheet image and splits it into individual character tiles.
 * The sprite sheet should be a 16x16 grid of characters (256 total).
 */
class ComposeCP437Tileset(
    private val resource: TilesetResource
) : Tileset<ComposeContext> {

    override val id: UUID = UUID.randomUUID()
    override val width: Int get() = resource.width
    override val height: Int get() = resource.height
    override val targetType: KClass<ComposeContext> = ComposeContext::class

    private var loadingState = LoadingState.NOT_LOADED

    // Each tile's pixel data as BGRA bytes with alpha extracted for color blending
    private var tileAlphas: List<ByteArray> = emptyList()

    init {
        require(resource.tileType == CHARACTER_TILE) {
            "CP437 tilesets only support ${CHARACTER_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: ComposeContext, position: Position) {
        if (loadingState == LoadingState.NOT_LOADED) {
            loadingState = LoadingState.LOADING
            loadSpriteSheet()
        }

        if (loadingState == LoadingState.LOADED) {
            require(tile is CharacterTile) {
                "A CP437 renderer can only render ${CharacterTile::class.simpleName}s. Offending tile: $tile."
            }

            var finalTile: CharacterTile = tile
            finalTile.modifiers.filterIsInstance<TileModifier<CharacterTile>>().forEach { modifier ->
                if (modifier.canTransform(finalTile)) {
                    finalTile = modifier.transform(finalTile)
                }
            }

            val px = position.x * width
            val py = position.y * height

            val bgArgb = finalTile.backgroundColor.toArgb()
            val fgArgb = finalTile.foregroundColor.toArgb()

            // Draw background
            context.fillRect(px, py, width, height, bgArgb)

            // Draw character sprite with foreground color
            val idx = finalTile.character.CP437Index()
            if (idx in tileAlphas.indices) {
                val alphas = tileAlphas[idx]
                blendTileWithColor(context, px, py, alphas, fgArgb)
            }
        }
    }

    private fun blendTileWithColor(context: ComposeContext, x: Int, y: Int, alphas: ByteArray, fgArgb: Int) {
        val fgR = (fgArgb shr 16) and 0xFF
        val fgG = (fgArgb shr 8) and 0xFF
        val fgB = fgArgb and 0xFF

        val pixelBuffer = context.pixelBuffer

        for (ty in 0 until height) {
            val py = y + ty
            if (py < 0 || py >= pixelBuffer.height) continue

            val rowOffset = py * pixelBuffer.width * 4
            val tileRowOffset = ty * width

            for (tx in 0 until width) {
                val px = x + tx
                if (px < 0 || px >= pixelBuffer.width) continue

                val alpha = alphas[tileRowOffset + tx].toInt() and 0xFF
                if (alpha == 0) continue

                val pixelOffset = rowOffset + px * 4

                if (alpha == 255) {
                    pixelBuffer.pixels[pixelOffset] = fgB.toByte()
                    pixelBuffer.pixels[pixelOffset + 1] = fgG.toByte()
                    pixelBuffer.pixels[pixelOffset + 2] = fgR.toByte()
                    pixelBuffer.pixels[pixelOffset + 3] = 0xFF.toByte()
                } else {
                    val bgB = pixelBuffer.pixels[pixelOffset].toInt() and 0xFF
                    val bgG = pixelBuffer.pixels[pixelOffset + 1].toInt() and 0xFF
                    val bgR = pixelBuffer.pixels[pixelOffset + 2].toInt() and 0xFF

                    val invAlpha = 255 - alpha
                    val r = (fgR * alpha + bgR * invAlpha) / 255
                    val g = (fgG * alpha + bgG * invAlpha) / 255
                    val b = (fgB * alpha + bgB * invAlpha) / 255

                    pixelBuffer.pixels[pixelOffset] = b.toByte()
                    pixelBuffer.pixels[pixelOffset + 1] = g.toByte()
                    pixelBuffer.pixels[pixelOffset + 2] = r.toByte()
                    pixelBuffer.pixels[pixelOffset + 3] = 0xFF.toByte()
                }
            }
        }
    }

    private fun loadSpriteSheet() {
        try {
            val imageBytes = when (resource.resourceType) {
                ResourceType.FILESYSTEM -> {
                    File(resource.path).readBytes()
                }
                ResourceType.PROJECT -> {
                    val classLoader = Thread.currentThread().contextClassLoader
                        ?: this::class.java.classLoader
                    classLoader.getResourceAsStream(resource.path)?.readBytes()
                        ?: error("Could not load tileset resource: ${resource.path}")
                }
            }

            val image = Image.makeFromEncoded(imageBytes)
            val bitmap = Bitmap()
            bitmap.allocPixels(ImageInfo.makeN32(image.width, image.height, ColorAlphaType.PREMUL))
            image.readPixels(bitmap, 0, 0)

            // Split into 16x16 grid (256 tiles)
            val tilesPerRow = 16
            val tiles = mutableListOf<ByteArray>()

            for (tileIdx in 0 until 256) {
                val tileX = (tileIdx % tilesPerRow) * width
                val tileY = (tileIdx / tilesPerRow) * height

                // Extract alpha channel for this tile
                val alphas = ByteArray(width * height)
                val pixelData = bitmap.readPixels(
                    ImageInfo.makeN32(width, height, ColorAlphaType.PREMUL),
                    width * 4, tileX, tileY
                )

                if (pixelData != null) {
                    for (i in 0 until width * height) {
                        // For white-on-transparent sprites, use the red channel as alpha
                        // (since white pixels have R=G=B=255 and the image is likely white on transparent)
                        val r = pixelData[i * 4 + 2].toInt() and 0xFF
                        val a = pixelData[i * 4 + 3].toInt() and 0xFF
                        // Use the minimum of alpha and luminance for proper blending
                        alphas[i] = minOf(r, a).toByte()
                    }
                }

                tiles.add(alphas)
            }

            tileAlphas = tiles
            bitmap.close()
            image.close()

            loadingState = LoadingState.LOADED
            println("Loaded CP437 tileset: ${resource.path} (${tiles.size} tiles)")
        } catch (e: Exception) {
            println("Failed to load CP437 tileset: ${resource.path}")
            e.printStackTrace()
            loadingState = LoadingState.LOADED
            tileAlphas = List(256) { ByteArray(width * height) }
        }
    }

    private fun Color.toArgb(): Int {
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }
}
