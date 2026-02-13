package org.hexworks.zircon.internal.graphics

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.builder.graphics.withStyleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.asCharacterTileOrNull
import org.hexworks.zircon.api.extensions.toCharacterTileString
import org.hexworks.zircon.api.graphics.TextWrap.NO_WRAPPING
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.fetchCharacters
import kotlin.test.Test

class DefaultCharacterTileStringTest {

    @Test
    fun shouldBuildStringWithDefaultProperly() {
        val result = characterTileString { +TEXT }

        val template = characterTile { }

        result.characterTiles shouldContainExactly listOf(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    @Test
    fun shouldProperlyCreateFromString() {
        val result = "foobar".toCharacterTileString()

        result.characterTiles.map { it.character } shouldContainExactly listOf('f', 'o', 'o', 'b', 'a', 'r')
    }

    @Test
    fun shouldBuildStringWithCustomProperly() {
        val result = characterTileString {
            +TEXT
            withStyleSet {
                backgroundColor = BACKGROUND
                foregroundColor = FOREGROUND
                modifiers = setOf(MODIFIER)
            }
            textWrap = NO_WRAPPING
        }

        val template = characterTile {
            withStyleSet {
                foregroundColor = FOREGROUND
                backgroundColor = BACKGROUND
                modifiers = setOf(MODIFIER)
            }
        }

        result.characterTiles shouldContainExactly listOf(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    @Test
    fun shouldTruncateWhenThereIsNoWrapAndDoesntFit() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }
        surface.draw(
            characterTileString {
                +TEXT
                textWrap = NO_WRAPPING
            }, Position.create(1, 1)
        )

        surface.fetchCharacters() shouldContainExactly listOf(
            ' ', ' ',
            ' ', 'T'
        )
    }

    @Test
    fun shouldProperlyWriteNoWrapOverlappingStringToTileGraphic() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }

        surface.draw(
            characterTileString {
                +TEXT
                textWrap = NO_WRAPPING
            }
        )

        surface.getTileAtOrNull(Position.create(0, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 'T'
        surface.getTileAtOrNull(Position.create(1, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 'E'
        surface.getTileAtOrNull(Position.create(0, 1))
            ?.asCharacterTileOrNull()
            ?.character shouldBe null
        surface.getTileAtOrNull(Position.create(1, 1))
            ?.asCharacterTileOrNull()
            ?.character shouldBe null
    }

    @Test
    fun when_word_wrap_is_used_then_it_should_work() {
        val surface = tileGraphics {
            withSize {
                width = 5
                height = 1
            }
        }

        val textCharacterString = characterTileString {
            +"atest"
            textWrap = WORD_WRAP
        }
        surface.draw(textCharacterString)

        // a and space should fit on the first line
        surface.getTileAtOrNull(Position.create(0, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 'a'
        surface.getTileAtOrNull(Position.create(1, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 't'
        surface.getTileAtOrNull(Position.create(2, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 'e'
        surface.getTileAtOrNull(Position.create(3, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 's'
        surface.getTileAtOrNull(Position.create(4, 0))
            ?.asCharacterTileOrNull()
            ?.character shouldBe 't'
    }

    @Test
    fun shouldWorkWhenWordWrappingWithMultipleWords() {
        val surface = tileGraphics {
            withSize {
                width = 4
                height = 2
            }
        }

        val textCharacterString = characterTileString {
            +"a test"
            textWrap = WORD_WRAP
            size = surface.size
        }
        surface.draw(textCharacterString)

        surface.fetchCharacters() shouldContainExactly listOf(
            'a', ' ', ' ', ' ',
            't', 'e', 's', 't'
        )
    }

    @Test
    fun wordWrapShouldWrapAsWordTooBigForSingleRow() {
        val surface = tileGraphics {
            withSize {
                width = 4
                height = 2
            }
        }

        val textCharacterString = characterTileString {
            +"atest"
            textWrap = WORD_WRAP
            size = surface.size
        }
        surface.draw(textCharacterString)

        surface.fetchCharacters() shouldContainExactly listOf(
            'a', 't', 'e', 's',
            't', ' ', ' ', ' '
        )

    }

    @Test
    fun wordWrapShouldWorkCorrectly() {
        val surface = tileGraphics {
            withSize {
                width = 5
                height = 4
            }
        }

        val textCharacterString = characterTileString {
            +"a test thghty"
            textWrap = WORD_WRAP
            size = surface.size
        }
        surface.draw(textCharacterString)

        surface.fetchCharacters() shouldContainExactly listOf(
            'a', ' ', ' ', ' ', ' ',
            't', 'e', 's', 't', ' ',
            't', 'h', 'g', 'h', 't',
            'y', ' ', ' ', ' ', ' '
        )
    }

    @Test
    fun shouldProperlyWriteNoWrapStringToTileGraphicWithOffset() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }

        surface.draw(
            characterTileString {
                +TEXT
                textWrap = NO_WRAPPING
                size = surface.size
            }, Position.OFFSET_1X1
        )

        surface.fetchCharacters() shouldContainExactly listOf(
            ' ', ' ',
            ' ', 'T'
        )


    }

    @Test
    fun shouldProperlyWriteWrapStringToTileGraphicWithoutOffset() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }

        surface.draw(
            characterTileString {
                +TEXT
                size = surface.size
            }
        )

        surface.fetchCharacters() shouldContainExactly listOf(
            'T', 'E',
            'X', 'T'
        )
    }

    @Test
    fun shouldProperlyWriteWrapStringToTileGraphicWithOffset() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }

        surface.draw(
            characterTileString {
                +TEXT
                size = surface.size
            }, Position.create(1, 0)
        )

        surface.fetchCharacters() shouldContainExactly listOf(
            ' ', 'T',
            ' ', 'X'
        )
    }

    @Test
    fun shouldProperlyWriteStringToTileGraphicWhenLengthIs1() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }

        surface.draw(
            characterTileString {
                +"T"
                size = surface.size
            }, Position.create(0, 0)
        )

        surface.fetchCharacters() shouldContainExactly listOf(
            'T', ' ',
            ' ', ' '
        )
    }

    @Test
    fun shouldProperlyTruncateStringWhenDoesNotFitOnTileGraphic() {
        val surface = tileGraphics {
            withSize {
                width = 2
                height = 2
            }
        }

        surface.draw(
            characterTileString {
                +"TEXTTEXT"
                size = surface.size
            }, Position.create(0, 0)
        )

        surface.fetchCharacters() shouldContainExactly listOf(
            'T', 'E',
            'X', 'T'
        )
    }

    @Test
    fun shouldAddTwoStringsTogetherProperly() {

        val string = characterTileString {
            +"TE"
        }

        val other = characterTileString {
            +"XT"
        }

        val template = characterTile { }

        string.plus(other).characterTiles shouldContainExactly listOf(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    companion object {
        val FOREGROUND = DefaultAnsiPalette[ANSIColor.RED]
        val BACKGROUND = DefaultAnsiPalette[ANSIColor.GREEN]
        val MODIFIER = Modifiers.blink()
        const val TEXT = "TEXT"
    }
}
