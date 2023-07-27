package org.hexworks.zircon.api.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.cobalt.databinding.api.extension.orElse
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Rect
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.impl.DrawWindow
import org.hexworks.zircon.fetchCharacters
import org.junit.Before
import org.junit.Test

class DrawWindowTest {

    private val backend = tileGraphics {
        size = BACKEND_SIZE_5X5
    }

    lateinit var target: DrawWindow

    @Before
    fun setUp() {
        target = backend.toDrawWindow(BOUNDS_1TO1_3X3)
    }

    @Test
    fun shouldProperlyFillBackendWhenFillIsCalled() {
        val subFiller = Tile.defaultTile().withCharacter('x')

        target.fill(subFiller)

        val chars = backend.fetchCharacters()

        assertThat(chars).containsExactly(
            ' ', ' ', ' ', ' ', ' ',
            ' ', 'x', 'x', 'x', ' ',
            ' ', 'x', 'x', 'x', ' ',
            ' ', 'x', 'x', 'x', ' ',
            ' ', ' ', ' ', ' ', ' '
        )
    }

    @Test
    fun shouldProperlyFetchCells() {
        assertThat(target.size.fetchPositions().toList())
            .containsExactlyElementsOf(BOUNDS_1TO1_3X3.size.fetchPositions().toList())
    }


    @Test
    fun shouldProperlyApplyStyle() {
        backend.fill(Tile.defaultTile())
        backend.applyStyle(BLUE_RED_STYLE)

        target.applyStyle(YELLOW_GREEN_STYLE)

        assertThat(backend.size.fetchPositions()
            .map { backend.getTileAtOrNull(it)?.styleSet }).containsExactly(
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
            .toDrawWindow(Rect.create(Position.offset1x1(), Size.create(2, 1)))
        result.fill(subFiller)

        val chars = backend.size.fetchPositions().map {
            backend.getTileAtOrNull(it)
                ?.let { tile -> tile.asCharacterTileOrNull()?.character }
                .orElse(' ')
        }

        assertThat(chars).containsExactly(
            ' ', ' ', ' ', ' ', ' ',
            ' ', ' ', ' ', ' ', ' ',
            ' ', ' ', 'x', 'x', ' ',
            ' ', ' ', ' ', ' ', ' ',
            ' ', ' ', ' ', ' ', ' '
        )

    }

    companion object {

        private val BLUE_RED_STYLE = styleSet {
            backgroundColor = BLUE
            foregroundColor = RED
        }
        private val YELLOW_GREEN_STYLE = styleSet {
            backgroundColor = YELLOW
            foregroundColor = GREEN
        }
        private val BACKEND_SIZE_5X5 = Size.create(5, 5)
        private val FILLER_UNDERSCORE = Tile.defaultTile().withCharacter('_')

        val BOUNDS_1TO1_3X3 = Rect.create(Position.offset1x1(), Size.create(3, 3))
    }

}
