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
                .backgroundColor(BG_COLOR)
                .foregroundColor(FG_COLOR)
                .character(CHAR)
                .tags(TAGS)
                .modifiers(MODIFIER)
                .build()

        assertThat(result).isEqualTo(
                Tile.create(
                        character = CHAR,
                        style = StyleSetBuilder.newBuilder()
                                .foregroundColor(FG_COLOR)
                                .backgroundColor(BG_COLOR)
                                .modifiers(MODIFIER)
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
