package org.hexworks.zircon.api.builder

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.data.tile.CharacterTile
import kotlin.test.Test

class TileBuilderTest {

    @Test
    fun shouldBuildProperTextCharacter() {

        val style = styleSet {
            backgroundColor = BG_COLOR
            foregroundColor = FG_COLOR
            modifiers = setOf(MODIFIER)
        }

        val result = characterTile {
            styleSet = style
            character = CHAR
        }

        result shouldBe CharacterTile(CHAR, style)
    }

    companion object {
        val BG_COLOR = Color.fromString("#aabbcc")
        val FG_COLOR = Color.fromString("#ccbbaa")
        const val CHAR = 'a'
        val MODIFIER = Modifiers.blink()
    }
}
