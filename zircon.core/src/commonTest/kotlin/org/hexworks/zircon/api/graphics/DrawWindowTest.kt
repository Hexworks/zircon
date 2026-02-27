package org.hexworks.zircon.api.graphics

import io.kotest.matchers.collections.shouldContainExactly
import org.hexworks.cobalt.databinding.api.extension.orElse
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.fetchCharacters
import kotlin.test.BeforeTest
import kotlin.test.Test

class DrawWindowTest {

    private val backend = tileGraphics {
        size = BACKEND_SIZE_5X5
    }

    lateinit var target: DrawWindow

    @BeforeTest
    fun setUp() {
        target = backend.toDrawWindow(BOUNDS_1TO1_3X3)
    }

    @Test
    fun shouldProperlyFillBackendWhenFillIsCalled() {
        val subFiller = Tile.defaultTile().withCharacter('x')

        target.fill(subFiller)

        val chars = backend.fetchCharacters()

        chars shouldContainExactly listOf(
            ' ', ' ', ' ', ' ', ' ',
            ' ', 'x', 'x', 'x', ' ',
            ' ', 'x', 'x', 'x', ' ',
            ' ', 'x', 'x', 'x', ' ',
            ' ', ' ', ' ', ' ', ' '
        )
    }

    @Test
    fun shouldProperlyFetchCells() {
        target.size.fetchPositions().toList() shouldContainExactly BOUNDS_1TO1_3X3.size.fetchPositions().toList()
    }


    @Test
    fun shouldProperlyApplyStyle() {
        backend.fill(Tile.defaultTile())
        backend.applyStyle(BLUE_RED_STYLE)

        target.applyStyle(YELLOW_GREEN_STYLE)

        backend.size.fetchPositions()
            .map { backend.getTileAtOrNull(it)?.asCharacterTileOrNull()?.styleSet } shouldContainExactly listOf(
            BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE,
            BLUE_RED_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, BLUE_RED_STYLE,
            BLUE_RED_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, BLUE_RED_STYLE,
            BLUE_RED_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, YELLOW_GREEN_STYLE, BLUE_RED_STYLE,
            BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE, BLUE_RED_STYLE
        )
    }

    @Test
    fun shouldProperlyCreateSubSubTileGraphics() {
        val subFiller = FILLER_UNDERSCORE.withCharacter('x')

        val result = target
            .toDrawWindow(Boundable.create(Position.OFFSET_1X1, Size.create(2, 1)))
        result.fill(subFiller)

        val chars = backend.size.fetchPositions().map {
            backend.getTileAtOrNull(it)
                ?.let { tile -> tile.asCharacterTileOrNull()?.character }
                .orElse(' ')
        }

        chars shouldContainExactly listOf(
            ' ', ' ', ' ', ' ', ' ',
            ' ', ' ', ' ', ' ', ' ',
            ' ', ' ', 'x', 'x', ' ',
            ' ', ' ', ' ', ' ', ' ',
            ' ', ' ', ' ', ' ', ' '
        )

    }

    companion object {

        private val BLUE_RED_STYLE = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.BLUE]
            foregroundColor = DefaultAnsiPalette[ANSIColor.RED]
        }
        private val YELLOW_GREEN_STYLE = styleSet {
            backgroundColor = DefaultAnsiPalette[ANSIColor.YELLOW]
            foregroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
        }
        private val BACKEND_SIZE_5X5 = Size.create(5, 5)
        private val FILLER_UNDERSCORE = Tile.defaultTile().withCharacter('_')

        val BOUNDS_1TO1_3X3 = Boundable.create(Position.OFFSET_1X1, Size.create(3, 3))
    }

}
