package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.layer
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.resource.TilesetResource
import org.hexworks.zircon.examples.EXAMPLE_TAB_WIDTH
import org.hexworks.zircon.examples.EXAMPLE_TOP
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import kotlin.random.Random

/**
 * The same scene in several tilesets side by side: every layer has its own tileset, so a
 * grass field with a title and random symbols is drawn once per tileset.
 */
public val tilesetExample: Example = Example("Tilesets") { context ->
    val tilesets = listOf(
        "Wanderlust" to CP437TilesetResources.wanderlust16x16(),
        "Rogue Yun" to CP437TilesetResources.rogueYun16x16(),
        "Bisasam" to CP437TilesetResources.bisasam16x16(),
    )
    val columnWidth = context.contentSize.width / tilesets.size
    val fieldSize = Size(columnWidth - 2, context.contentSize.height - 2)
    val layers = tilesets.mapIndexed { index, (name, tileset) ->
        field(name, tileset, fieldSize, Position(EXAMPLE_TAB_WIDTH + index * columnWidth + 1, EXAMPLE_TOP + 1))
    }
    ExampleView(context.emptyContent(), layers = layers)
}

private fun field(title: String, tileset: TilesetResource, size: Size, offset: Position) = layer {
    this.size = size
    this.offset = offset
    this.tileset = tileset
}.apply {
    val random = Random(title.hashCode())
    for (y in 0 until size.height) for (x in 0 until size.width) {
        draw(GRASSES[random.nextInt(GRASSES.size)], Position(x, y))
    }
    "$title ${tileset.width}x${tileset.height}".forEachIndexed { i, char ->
        draw(characterTile {
            character = char
            withStyleSet {
                foregroundColor = TEXT_COLOR
                backgroundColor = TEXT_BACKGROUND
            }
        }, Position(i + 1, 1))
    }
    val colors = ANSIColor.entries
    repeat(RANDOM_CHAR_COUNT) {
        draw(characterTile {
            character = RANDOM_CHARS[random.nextInt(RANDOM_CHARS.size)]
            withStyleSet {
                foregroundColor = DefaultAnsiPalette[colors[random.nextInt(colors.size)]]
                backgroundColor = Color.TRANSPARENT
            }
        }, Position(random.nextInt(size.width), random.nextInt(size.height - 3) + 3))
    }
}

private fun grass(char: Char, fg: String, bg: String) = characterTile {
    character = char
    withStyleSet {
        foregroundColor = Color.fromString(fg)
        backgroundColor = Color.fromString(bg)
    }
}

private val GRASSES = listOf(
    grass(',', "#33cc44", "#114911"),
    grass('`', "#33bb44", "#114511"),
    grass('\'', "#33aa44", "#114011"),
)
private val TEXT_COLOR = Color.fromString("#dd6644")
private val TEXT_BACKGROUND = Color.fromString("#00ff00")
private val RANDOM_CHARS = charArrayOf(
    Symbols.FACE_BLACK, Symbols.FACE_WHITE, Symbols.CLUB, Symbols.TRIANGLE_UP_POINTING_BLACK,
    Symbols.SPADES, Symbols.BULLET, Symbols.DIAMOND,
)
private const val RANDOM_CHAR_COUNT = 30
