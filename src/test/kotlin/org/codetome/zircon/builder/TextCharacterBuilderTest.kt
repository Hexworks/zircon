package org.codetome.zircon.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.Modifier
import org.codetome.zircon.TextCharacter
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
                TextCharacter(
                        character = CHAR,
                        foregroundColor = FG_COLOR,
                        backgroundColor = BG_COLOR,
                        modifiersToUse = setOf(MODIFIER)))
    }

    companion object {
        val BG_COLOR = TextColorFactory.fromString("#aabbcc")
        val FG_COLOR = TextColorFactory.fromString("#ccbbaa")
        val CHAR = 'a'
        val MODIFIER = Modifier.BOLD
    }
}