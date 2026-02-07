package org.hexworks.zircon.api.data

import io.kotest.matchers.collections.shouldBeEmpty
import io.kotest.matchers.collections.shouldContainExactlyInAnyOrder
import io.kotest.matchers.shouldBe
import io.kotest.matchers.types.shouldBeSameInstanceAs
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.tile.CharacterTile
import org.hexworks.zircon.api.extensions.isBlinking
import org.hexworks.zircon.api.extensions.isNotEmpty
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.api.modifier.SimpleModifiers.Hidden
import kotlin.test.Test

class DefaultTileTest {

    @Test
    fun shouldProperlyReportTileType() {
        Tile.defaultTile().tileType shouldBe TileType.CHARACTER_TILE
    }

    @Test
    fun shouldProperlyAddNewModifiersAndKeepOldOnesWhenWithAddedModifiersIsCalled() {
        val target = characterTile {
            character = 'x'
            withStyleSet {
                modifiers = setOf(Hidden)
            }
        }

        target.withAddedModifiers(Blink).modifiers shouldContainExactlyInAnyOrder listOf(Hidden, Blink)
    }

    @Test
    fun shouldProperlyRemoveAllModifiersWhenWithoutModifiersIsCalled() {
        val target = characterTile {
            character = 'x'
            withStyleSet {
                modifiers = setOf(Blink)
            }
        }

        target.withNoModifiers().modifiers.shouldBeEmpty()
    }

    @Test
    fun shouldReturnSameTileWhenWithNoModifiersIsCalledAndItHasNoModifiers() {
        val target = characterTile {
            character = 'x'
        }

        target.withNoModifiers() shouldBeSameInstanceAs target
    }

    @Test
    fun shouldReturnSameTileWhenWithAddedModifiersIsCalledAndItAlreadyHasTheModifiers() {
        val target = characterTile {
            character = 'x'
            withStyleSet {
                modifiers = setOf(Blink, Hidden)
            }
        }

        target.withAddedModifiers(Blink, Hidden) shouldBeSameInstanceAs target
    }

    @Test
    fun shouldProperlyCreateCopy() {
        val tile = characterTile {
            character = 'x'
            withStyleSet {
                backgroundColor = DefaultAnsiPalette[ANSIColor.YELLOW]
            }
        }

        tile.createCopy() shouldBe tile
        tile.createCopy() shouldNotBeSameInstanceAs tile
    }

    @Test
    fun shouldGenerateProperCacheKey() {
        val result = characterTile {
            character = 'x'
            withStyleSet {
                backgroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
                foregroundColor = Color.fromString("#aabbcc")
                modifiers = setOf(Blink)
            }
        }.cacheKey

        result shouldBe "CharacterTile(c=x,s=StyleSet(fg=TextColor(r=170,g=187,b=204,a=255),bg=TextColor(r=0,g=196,b=0,a=255),m=[Modifier.Blink]))"
    }

    @Test
    fun defaultCharacterShouldBeEmptyStringWithBlackAndWhiteAndNoModifiers() {
        Tile.defaultTile().character shouldBe ' '
        Tile.defaultTile().backgroundColor shouldBe DefaultAnsiPalette[ANSIColor.BLACK]
        Tile.defaultTile().foregroundColor shouldBe DefaultAnsiPalette[ANSIColor.WHITE]
        Tile.defaultTile().modifiers.shouldBeEmpty()
    }

    @Test
    fun shouldNotBeEmptyWhenNotEmpty() {
        Tile.defaultTile().isNotEmpty shouldBe true
    }

    @Test
    fun shouldBeEmptyWhenEmpty() {
        Tile.empty().isNotEmpty shouldBe false
    }

    @Test
    fun shouldProperlyRemoveModifiersWhenWithoutModifiersIsCalled() {
        characterTile {
            withStyleSet {
                modifiers = setOf(Blink)
            }
        }.withRemovedModifiers(setOf(Blink)).modifiers.shouldBeEmpty()
    }

    @Test
    fun shouldProperlyCreateCopyWithStyleWhenWithStyleIsCalled() {
        val style = styleSet {
            foregroundColor = DefaultAnsiPalette[ANSIColor.BLUE]
            backgroundColor = DefaultAnsiPalette[ANSIColor.CYAN]
            modifiers = setOf(Blink)
        }

        val copy = characterTile {
            styleSet = style
        }

        copy.modifiers shouldBe style.modifiers
        copy.backgroundColor shouldBe style.backgroundColor
        copy.foregroundColor shouldBe style.foregroundColor
    }

    @Test
    fun blinkingModifierShouldBeBlinking() {
        characterTile {
            withStyleSet {
                modifiers = setOf(Modifiers.blink())
            }
        }.isBlinking shouldBe true
    }

    @Test
    fun shouldBeSameButWithCharChangedWhenWithCharIsCalled() {
        characterTile {
            character = 'a'
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = EXPECTED_MODIFIERS
            }
        }.withCharacter(EXPECTED_CHAR) shouldBe EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldBeSameButWithFGChangedWhenWithForegroundColorIsCalled() {
        characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = EXPECTED_MODIFIERS
            }
        }.withForegroundColor(EXPECTED_FG_COLOR) shouldBe EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldBeSameButWithBGChangedWhenWithBackgroundColorIsCalled() {
        characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = DefaultAnsiPalette[ANSIColor.RED]
                modifiers = EXPECTED_MODIFIERS
            }
        }.withBackgroundColor(EXPECTED_BG_COLOR) shouldBe EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldBeSameButWithModifiersChangedWhenWithModifiersIsCalled() {
        characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = setOf(Hidden)
            }
        }.withModifiers(EXPECTED_MODIFIERS) shouldBe EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithModifierIsCalled() {
        characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = setOf(Hidden)
            }
        }.withModifiers(Blink) shouldBe characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = setOf(Blink)
            }
        }
    }

    @Test
    fun shouldBeSameButWithModifierRemovedWhenWithRemovedModifierIsCalled() {
        characterTile {
            character = EXPECTED_CHAR
            withStyleSet {
                foregroundColor = EXPECTED_FG_COLOR
                backgroundColor = EXPECTED_BG_COLOR
                modifiers = EXPECTED_MODIFIERS + Hidden
            }
        }.withRemovedModifiers(Hidden) shouldBe EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithCharIsCalledWithSameChar() {
        EXPECTED_CHARACTER_TILE.withCharacter(EXPECTED_CHAR) shouldBeSameInstanceAs EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithFGColorIsCalledWithSameFGColor() {
        EXPECTED_CHARACTER_TILE.withForegroundColor(EXPECTED_FG_COLOR) shouldBeSameInstanceAs EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithBGColorIsCalledWithSameBGColor() {
        EXPECTED_CHARACTER_TILE.withBackgroundColor(EXPECTED_BG_COLOR) shouldBeSameInstanceAs EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifiersIsCalledWithSameModifier() {
        EXPECTED_CHARACTER_TILE.withModifiers(Blink) shouldBeSameInstanceAs EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithModifiersSIsCalledWithSameModifierS() {
        EXPECTED_CHARACTER_TILE.withModifiers(EXPECTED_MODIFIERS) shouldBeSameInstanceAs EXPECTED_CHARACTER_TILE
    }

    @Test
    fun shouldReturnSameTextCharacterWhenWithRemovedModifiersIsCalledWithNonPresentModifier() {
        EXPECTED_CHARACTER_TILE.withRemovedModifiers(Hidden) shouldBeSameInstanceAs EXPECTED_CHARACTER_TILE
    }

    companion object {
        const val EXPECTED_CHAR = 'x'
        val EXPECTED_FG_COLOR = Color.fromString("#aabbcc")
        val EXPECTED_BG_COLOR = Color.fromString("#223344")
        val EXPECTED_MODIFIERS = setOf(Blink)

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
