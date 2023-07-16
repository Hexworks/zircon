package org.hexworks.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.internal.data.DefaultCharacterTile
import org.junit.Test

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

        assertThat(result).isEqualTo(
            DefaultCharacterTile(
                CHAR, style
            )
        )
    }

    companion object {
        val BG_COLOR = TileColor.fromString("#aabbcc")
        val FG_COLOR = TileColor.fromString("#ccbbaa")
        val CHAR = 'a'
        val MODIFIER = Modifiers.crossedOut()
    }
}
