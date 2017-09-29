package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.Modifiers
import org.codetome.zircon.internal.DefaultTextCharacter
import org.codetome.zircon.api.factory.TextColorFactory
import org.junit.Test

class TextCharacterBuilderTest {

    @Test
    fun shouldBuildProperTextCharacter() {
        val result = TextCharacterBuilder.newBuilder()
                .backgroundColor(BG_COLOR)
                .foregroundColor(FG_COLOR)
                .character(CHAR)
                .tag(*TAGS.toTypedArray())
                .tags(TAGS)
                .modifier(MODIFIER)
                .build()

        assertThat(result).isEqualTo(
                DefaultTextCharacter.of(
                        character = CHAR,
                        foregroundColor = FG_COLOR,
                        tags = TAGS,
                        backgroundColor = BG_COLOR,
                        modifiers = setOf(MODIFIER)))
    }

    companion object {
        val BG_COLOR = TextColorFactory.fromString("#aabbcc")
        val FG_COLOR = TextColorFactory.fromString("#ccbbaa")
        val TAGS = setOf("TAG1", "TAG2")
        val CHAR = 'a'
        val MODIFIER = Modifiers.BOLD
    }
}