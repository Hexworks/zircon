package org.hexworks.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.Modifiers
import org.junit.Test

class TileBuilderTest {

    @Test
    fun shouldBuildProperTextCharacter() {
        val result = TileBuilder.newBuilder()
                .withBackgroundColor(BG_COLOR)
                .withForegroundColor(FG_COLOR)
                .withCharacter(CHAR)
                .withTags(TAGS)
                .withModifiers(MODIFIER)
                .build()

        assertThat(result).isEqualTo(
                Tile.createCharacterTile(
                        character = CHAR,
                        style = StyleSetBuilder.newBuilder()
                                .withForegroundColor(FG_COLOR)
                                .withBackgroundColor(BG_COLOR)
                                .withModifiers(MODIFIER)
                                .build()))
    }

    companion object {
        val BG_COLOR = TileColor.fromString("#aabbcc")
        val FG_COLOR = TileColor.fromString("#ccbbaa")
        val TAGS = setOf("TAG1", "TAG2")
        val CHAR = 'a'
        val MODIFIER = Modifiers.crossedOut()
    }
}
