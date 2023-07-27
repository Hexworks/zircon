package org.hexworks.zircon.api.data

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers.blink
import org.hexworks.zircon.api.Modifiers.crossedOut
import org.hexworks.zircon.api.Modifiers.underline
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.modifier.border
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.extensions.*
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.internal.resource.TileType
import org.junit.Test

class DefaultTileTest {

    @Test
    fun shouldProperlyReportTileType() {
        assertThat(Tile.defaultTile().tileType).isEqualTo(TileType.CHARACTER_TILE)
    }

    @Test
    fun shouldProperlyAddNewModifiersAndKeepOldOnesWhenWithAddedModifiersIsCalled() {
        val target = characterTile {
            character = 'x'
            withStyleSet {
                modifiers = setOf(Underline)
            }
        }

        assertThat(target.withAddedModifiers(Blink).modifiers)
            .containsExactlyInAnyOrder(Underline, Blink)
    }

    @Test
    fun shouldProperlyRemoveAllModifiersWhenWithoutModifiersIsCalled() {
        val target = characterTile {
            character = 'x'
            withStyleSet {
                modifiers = setOf(Blink)
            }
        }

        assertThat(target.withNoModifiers().modifiers).isEmpty()
    }

    @Test
    fun shouldReturnSameTileWhenWithNoModifiersIsCalledAndItHasNoModifiers() {
        val target = characterTile {
            character = 'x'
        }

        assertThat(target.withNoModifiers()).isSameAs(target)
    }

    @Test
    fun shouldReturnSameTileWhenWithAddedModifiersIsCalledAndItAlreadyHasTheModifiers() {
        val target = characterTile {
            character = 'x'
            withStyleSet {
                modifiers = setOf(Blink, CrossedOut)
            }
        }

        assertThat(target.withAddedModifiers(Blink, CrossedOut)).isSameAs(target)
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val tile = characterTile {
            character = 'x'
            withStyleSet {
                backgroundColor = YELLOW
            }
        }

        assertThat(tile.createCopy())
            .isEqualTo(tile)
            .isNotSameAs(tile)
    }

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = characterTile {
            character = 'x'
            withStyleSet {
                backgroundColor = GREEN
                foregroundColor = TileColor.fromString("#aabbcc")
                modifiers = setOf(CrossedOut)
            }
        }.cacheKey

        assertThat(result).isEqualTo("CharacterTile(c=x,s=StyleSet(fg=TextColor(r=170,g=187,b=204,a=255),bg=TextColor(r=0,g=128,b=0,a=255),m=[Modifier.CrossedOut]))")
    }

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        assertThat(Tile.defaultTile().character).isEqualTo(' ')
        assertThat(Tile.defaultTile().backgroundColor).isEqualTo(BLACK)
        assertThat(Tile.defaultTile().foregroundColor).isEqualTo(WHITE)
        assertThat(Tile.defaultTile().modifiers).isEmpty()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsBorder() {
        assertThat(
            characterTile {
                withStyleSet {
                    modifiers = setOf(border { })
                }
            }.hasBorder
        ).isTrue()
    }

    @Test
    fun shouldProperlyReportHavingABorderWhenThereIsNoBorder() {
        assertThat(
            characterTile { }.hasBorder
        ).isFalse()
    }

    @Test
    fun shouldNotBeEmptyWhenNotEmpty() {
        assertThat(Tile.defaultTile().isNotEmpty).isTrue()
    }

    @Test
    fun shouldBeEmptyWhenEmpty() {
        assertThat(Tile.empty().isNotEmpty).isFalse()
    }

    @Test
    fun shouldProperlyRemoveModifiersWhenWithoutModifiersIsCalled() {
        assertThat(
            characterTile {
                withStyleSet {
                    modifiers = setOf(crossedOut())
                }
            }.withRemovedModifiers(setOf(crossedOut())).modifiers
        ).isEmpty()
    }

    @Test
    fun shouldProperlyCreateCopyWithStyleWhenWithStyleIsCalled() {
        val style = styleSet {
            foregroundColor = BLUE
            backgroundColor = CYAN
            modifiers = setOf(crossedOut())
        }

        val copy = characterTile {
            styleSet = style
        }

        assertThat(copy.modifiers).isEqualTo(style.modifiers)
        assertThat(copy.backgroundColor).isEqualTo(style.backgroundColor)
        assertThat(copy.foregroundColor).isEqualTo(style.foregroundColor)
    }

    @Test
    fun underlinedModifierShouldBeUnderlined() {
        assertThat(characterTile {
            withStyleSet {
                modifiers = setOf(underline())
            }
        }.isUnderlined).isTrue()
    }

    @Test
    fun crossedOutModifierShouldBeCrossedOut() {
        assertThat(
            characterTile {
                withStyleSet {
                    modifiers = setOf(crossedOut())
                }
            }.isCrossedOut
        ).isTrue()
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        assertThat(characterTile {
            withStyleSet {
                modifiers = setOf(blink())
            }
        }.isBlinking).isTrue()
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        assertThat(
            characterTile {
                character = 'a'
                withStyleSet {
                    foregroundColor = EXPECTED_FG_COLOR
                    backgroundColor = EXPECTED_BG_COLOR
                    modifiers = EXPECTED_MODIFIERS
                }
            }.withCharacter(EXPECTED_CHAR)
        ).isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        assertThat(
            characterTile {
                character = EXPECTED_CHAR
                withStyleSet {
                    foregroundColor = GREEN
                    backgroundColor = EXPECTED_BG_COLOR
                    modifiers = EXPECTED_MODIFIERS
                }
            }.withForegroundColor(EXPECTED_FG_COLOR)
        ).isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        assertThat(
            characterTile {
                character = EXPECTED_CHAR
                withStyleSet {
                    foregroundColor = EXPECTED_FG_COLOR
                    backgroundColor = RED
                    modifiers = EXPECTED_MODIFIERS
                }
            }.withBackgroundColor(EXPECTED_BG_COLOR)
        ).isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        assertThat(
            characterTile {
                character = EXPECTED_CHAR
                withStyleSet {
                    foregroundColor = EXPECTED_FG_COLOR
                    backgroundColor = EXPECTED_BG_COLOR
                    modifiers = setOf(Blink)
                }
            }.withModifiers(EXPECTED_MODIFIERS)
        ).isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithModifierIsCalled() {
        assertThat(
            characterTile {
                character = EXPECTED_CHAR
                withStyleSet {
                    foregroundColor = EXPECTED_FG_COLOR
                    backgroundColor = EXPECTED_BG_COLOR
                    modifiers = setOf(CrossedOut)
                }
            }.withModifiers(Blink)
        ).isEqualTo(
            characterTile {
                character = EXPECTED_CHAR
                withStyleSet {
                    foregroundColor = EXPECTED_FG_COLOR
                    backgroundColor = EXPECTED_BG_COLOR
                    modifiers = setOf(Blink)
                }
            }
        )
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithRemovedModifierIsCalled() {
        assertThat(
            characterTile {
                character = EXPECTED_CHAR
                withStyleSet {
                    foregroundColor = EXPECTED_FG_COLOR
                    backgroundColor = EXPECTED_BG_COLOR
                    modifiers = EXPECTED_MODIFIERS + Blink
                }
            }.withRemovedModifiers(Blink)
        ).isEqualTo(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithCharIsCalledWithSameChar() {
        assertThat(EXPECTED_CHARACTER_TILE.withCharacter(EXPECTED_CHAR))
            .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithFGColorIsCalledWithSameFGColor() {
        assertThat(EXPECTED_CHARACTER_TILE.withForegroundColor(EXPECTED_FG_COLOR))
            .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithBGColorIsCalledWithSameBGColor() {
        assertThat(EXPECTED_CHARACTER_TILE.withBackgroundColor(EXPECTED_BG_COLOR))
            .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifiersIsCalledWithSameModifier() {
        assertThat(EXPECTED_CHARACTER_TILE.withModifiers(CrossedOut, Underline))
            .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifiersSIsCalledWithSameModifierS() {
        assertThat(EXPECTED_CHARACTER_TILE.withModifiers(EXPECTED_MODIFIERS))
            .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithRemovedModifiersIsCalledWithNonPresentModifier() {
        assertThat(EXPECTED_CHARACTER_TILE.withRemovedModifiers(Blink))
            .isSameAs(EXPECTED_CHARACTER_TILE)
    }

    companion object {
        const val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = TileColor.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = TileColor.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(CrossedOut, Underline)

        val EXPECTED_CHARACTER_TILE = characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = EXPECTED_MODIFIERS
            }
        }
    }

}
