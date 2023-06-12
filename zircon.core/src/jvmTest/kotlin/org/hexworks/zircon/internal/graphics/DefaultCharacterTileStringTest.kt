package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.characterTileString
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.builder.graphics.withStyleSet
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.toCharacterTileString
import org.hexworks.zircon.api.graphics.TextWrap.NO_WRAPPING
import org.hexworks.zircon.api.graphics.TextWrap.WORD_WRAP
import org.hexworks.zircon.fetchCharacters
import org.junit.Test

class DefaultCharacterTileStringTest {

    @Test
    fun shouldBuildStringWithDefaultProperly() {
        val result = characterTileString { +TEXT }

        val template = characterTile { }

        assertThat(result.characterTiles).containsExactly(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    @Test
    fun shouldProperlyCreateFromString() {
        val result = "foobar".toCharacterTileString()

        assertThat(result.characterTiles.map { it.character })
            .containsExactly('f', 'o', 'o', 'b', 'a', 'r')
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

        assertThat(result.characterTiles).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(
            surface.getTileAtOrNull(Position.create(0, 0))
                ?.asCharacterTileOrNull()
                ?.character
        )
            .isEqualTo('T')
        assertThat(
            surface.getTileAtOrNull(Position.create(1, 0))
                ?.asCharacterTileOrNull()
                ?.character
        )
            .isEqualTo('E')
        assertThat(
            surface.getTileAtOrNull(Position.create(0, 1))
                ?.asCharacterTileOrNull()
                ?.character
        ).isNull()
        assertThat(
            surface.getTileAtOrNull(Position.create(1, 1))
                ?.asCharacterTileOrNull()
                ?.character
        ).isNull()
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
        assertThat(
            surface.getTileAtOrNull(Position.create(0, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('a')
        assertThat(
            surface.getTileAtOrNull(Position.create(1, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('t')
        assertThat(
            surface.getTileAtOrNull(Position.create(2, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('e')
        assertThat(
            surface.getTileAtOrNull(Position.create(3, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('s')
        assertThat(
            surface.getTileAtOrNull(Position.create(4, 0))
                ?.asCharacterTileOrNull()

                ?.character
        ).isEqualTo('t')
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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
            }, Position.offset1x1()
        )

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(surface.fetchCharacters()).containsExactly(
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

        assertThat(string.plus(other).characterTiles).containsExactly(
            template.withCharacter('T'),
            template.withCharacter('E'),
            template.withCharacter('X'),
            template.withCharacter('T')
        )
    }

    companion object {
        val FOREGROUND = ANSITileColor.RED
        val BACKGROUND = ANSITileColor.GREEN
        val MODIFIER = Modifiers.crossedOut()
        const val TEXT = "TEXT"
    }
}
