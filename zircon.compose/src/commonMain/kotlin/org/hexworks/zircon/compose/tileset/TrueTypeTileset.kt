package org.hexworks.zircon.compose.tileset

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Canvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.drawText
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.sp
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.api.resource.loadBytes
import org.hexworks.zircon.compose.FrameData
import org.hexworks.zircon.compose.shader.AtlasInfo

/**
 * A monospace TrueType font turned into a glyph atlas on demand: the first time a
 * character is drawn it is rasterized (white on transparent) into the next free cell of
 * the atlas, and the atlas version bumps so the shader re-uploads it.
 */
public class TrueTypeTileset(resource: TilesetResource) : ComposeTileset(resource) {

    private var atlasId: Int = FrameData.NO_ATLAS
    private var atlas: ImageBitmap? = null
    private var version = 0
    private val slots = HashMap<Char, Int>()
    private val density = Density(1f)
    private lateinit var style: TextStyle
    private lateinit var measurer: TextMeasurer

    override suspend fun load() {
        if (loaded) return
        val family = fontFamilyFromBytes(resource.path.substringAfterLast('/'), resource.loadBytes())
        measurer = createTextMeasurer()
        // Size by the cell height, then shrink until a wide glyph and its line height fit the
        // slot: a glyph that spills over would land in the neighbouring slots of the atlas.
        val nominal = TextStyle(fontFamily = family, fontSize = (height * FONT_SIZE_RATIO).sp, color = Color.White)
        val probe = measurer.measure(PROBE_TEXT, nominal, density = density).size
        val fit = minOf(1f, width.toFloat() / probe.width, height.toFloat() / probe.height)
        style = nominal.copy(fontSize = (height * FONT_SIZE_RATIO * fit).sp)
        atlas = ImageBitmap(COLUMNS * width, ROWS * height)
        atlasId = putAtlas(AtlasInfo(atlas!!, width, height, version))
        loaded = true
    }

    override fun drawTile(tile: Tile, context: FrameData, position: Position) {
        val characterTile = tile as? CharacterTile ?: return
        if (!loaded) {
            context.writeBackground(position, characterTile.backgroundColor)
            return
        }
        val slot = slotFor(characterTile.character)
        context.writeGlyph(
            position = position,
            atlasId = atlasId,
            slotX = slot % COLUMNS,
            slotY = slot / COLUMNS,
            foreground = characterTile.foregroundColor,
            background = characterTile.backgroundColor,
        )
    }

    private fun slotFor(char: Char): Int = slots[char] ?: rasterize(char)

    private fun rasterize(char: Char): Int {
        val slot = slots.size.coerceAtMost(COLUMNS * ROWS - 1)
        slots[char] = slot
        if (char == ' ') return slot
        val image = atlas ?: return slot
        val layout = measurer.measure(char.toString(), style, density = density)
        val slotLeft = (slot % COLUMNS * width).toFloat()
        val slotTop = (slot / COLUMNS * height).toFloat()
        val x = slotLeft + (width - layout.size.width) / 2f
        val y = slotTop + (height - layout.size.height) / 2f
        CanvasDrawScope().draw(density, LayoutDirection.Ltr, Canvas(image), Size(image.width.toFloat(), image.height.toFloat())) {
            // Clipped to the slot: whatever still overhangs must not bleed into other glyphs.
            clipRect(slotLeft, slotTop, slotLeft + width, slotTop + height) {
                drawText(layout, topLeft = Offset(x, y))
            }
        }
        version++
        putAtlas(AtlasInfo(image, width, height, version), atlasId)
        return slot
    }

    private companion object {
        const val COLUMNS = 32
        const val ROWS = 32
        const val FONT_SIZE_RATIO = 1.0f
        const val PROBE_TEXT = "W"
    }
}

/**
 * Loads a font from its bytes; platform-specific because font parsing is.
 */
internal expect fun fontFamilyFromBytes(name: String, bytes: ByteArray): FontFamily

/**
 * A text measurer with the platform's default font resolver, usable outside composition.
 */
internal expect fun createTextMeasurer(): TextMeasurer
