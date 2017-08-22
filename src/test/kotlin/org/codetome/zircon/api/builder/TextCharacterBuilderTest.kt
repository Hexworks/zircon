package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifier
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.api.builder.TextCharacterBuilder
import org.codetome.zircon.api.factory.TextColorFactory
import org.junit.Test

class TextCharacterBuilderTest {

    @Test
    fun shouldBuildProperTextCharacter() {
        val result = TextCharacterBuilder.newBuilder()
                .backgroundColor(BG_COLOR)
                .foregroundColor(FG_COLOR)
                .character(CHAR)
                .modifier(MODIFIER)
                .build()

        assertThat(result).isEqualTo(
                TextCharacter.of(
                        character = CHAR,
                        foregroundColor = FG_COLOR,
                        backgroundColor = BG_COLOR,
                        modifiers = setOf(MODIFIER)))
    }

    companion object {
        val BG_COLOR = TextColorFactory.fromString("#aabbcc")
        val FG_COLOR = TextColorFactory.fromString("#ccbbaa")
        val CHAR = 'a'
        val MODIFIER = Modifier.BOLD
    }
}