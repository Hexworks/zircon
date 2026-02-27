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
import org.hexworks.zircon.renderer.compose.ComposeContext
import java.io.File
import kotlin.reflect.KClass

/**
 * TrueType font tileset implementation for Compose.
 *
 * Loads the TTF font from the resource and creates its own glyph cache.
 * Each tileset instance manages its own font and glyph cache.
 */
class ComposeTrueTypeFontTileset(
    private val resource: TilesetResource
) : Tileset<ComposeContext> {

    override val id: UUID = UUID.randomUUID()
    override val width: Int get() = resource.width
    override val height: Int get() = resource.height
    override val targetType: KClass<ComposeContext> = ComposeContext::class

    private var loadingState = LoadingState.NOT_LOADED
    private var glyphCache: SkiaGlyphCache? = null

    init {
        require(resource.tileType == CHARACTER_TILE) {
            "TrueType font tilesets only support ${CHARACTER_TILE.name}s. The supplied resource's type was ${resource.tileType.name}."
        }
    }

    override fun drawTile(tile: Tile, context: ComposeContext, position: Position) {
        if (loadingState == LoadingState.NOT_LOADED) {
            loadingState = LoadingState.LOADING
            loadFont()
        }

        if (loadingState == LoadingState.LOADED) {
            require(tile is CharacterTile) {
                "A TrueType font renderer can only render ${CharacterTile::class.simpleName}s. Offending tile: $tile."
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

            // Draw character using this tileset's glyph cache
            val glyph = glyphCache?.getGlyph(finalTile.character)
            if (glyph != null) {
                context.blendGlyph(glyph, px, py, fgArgb)
            }
        }
    }

    private fun loadFont() {
        try {
            val fontBytes = when (resource.resourceType) {
                ResourceType.FILESYSTEM -> {
                    File(resource.path).readBytes()
                }
                ResourceType.PROJECT -> {
                    val classLoader = Thread.currentThread().contextClassLoader
                        ?: this::class.java.classLoader
                    classLoader.getResourceAsStream(resource.path)?.readBytes()
                        ?: error("Could not load font resource: ${resource.path}")
                }
            }

            val typeface = createTypeface(fontBytes)
            glyphCache = SkiaGlyphCache(width, height, typeface)
            loadingState = LoadingState.LOADED
            println("Loaded TrueType font: ${resource.path}")
        } catch (e: Exception) {
            println("Failed to load TrueType font: ${resource.path}")
            e.printStackTrace()
            // Fall back to default typeface
            val typeface = createDefaultTypeface()
            glyphCache = SkiaGlyphCache(width, height, typeface)
            loadingState = LoadingState.LOADED
        }
    }

    private fun Color.toArgb(): Int {
        return (alpha shl 24) or (red shl 16) or (green shl 8) or blue
    }
}
