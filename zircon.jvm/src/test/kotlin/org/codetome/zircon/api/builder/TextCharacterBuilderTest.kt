package org.codetome.zircon.api.builder

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.TextCharacter
import org.codetome.zircon.platform.factory.TextColorFactory
import org.codetome.zircon.api.interop.Modifiers
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
                .modifiers(MODIFIER)
                .build()

        assertThat(result).isEqualTo(
                TextCharacter.of(
                        character = CHAR,
                        tags = TAGS,
                        styleSet = StyleSetBuilder.newBuilder()
                                .foregroundColor(FG_COLOR)
                                .backgroundColor(BG_COLOR)
                                .modifiers(MODIFIER)
                                .build()))
    }

    companion object {
        val BG_COLOR = TextColorFactory.fromString("#aabbcc")
        val FG_COLOR = TextColorFactory.fromString("#ccbbaa")
        val TAGS = setOf("TAG1", "TAG2")
        val CHAR = 'a'
        val MODIFIER = Modifiers.BOLD
    }
}
