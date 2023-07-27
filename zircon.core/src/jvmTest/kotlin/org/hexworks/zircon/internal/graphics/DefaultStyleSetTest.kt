package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.builder.modifier.border
import org.hexworks.zircon.api.color.ANSITileColor.WHITE
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.BorderPosition.TOP
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.api.modifier.SimpleModifiers.CrossedOut
import org.junit.Test

class DefaultStyleSetTest {

    @Test
    fun shouldBuildProperCacheKey() {
        val result = styleSet {
            backgroundColor = WHITE
            foregroundColor = TileColor.fromString("#aabbcc")
            modifiers = setOf(CrossedOut, border {
                borderPositions = setOf(TOP)
            })
        }.cacheKey
        assertThat(result).isEqualTo("StyleSet(fg=TextColor(r=170,g=187,b=204,a=255),bg=TextColor(r=192,g=192,b=192,a=255),m=[Modifier.CrossedOut,Modifier.Border(t=SOLID,bp=[TOP])])")
    }

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(CrossedOut)

        val result = styleSet.withAddedModifiers(Blink)

        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
        assertThat(styleSet.modifiers).containsExactlyInAnyOrder(CrossedOut)
        assertThat(result.modifiers).containsExactlyInAnyOrder(CrossedOut, Blink)
    }

    @Test
    fun shouldRemoveModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(CrossedOut, Blink)

        val result = styleSet.withRemovedModifiers(Blink)

        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
        assertThat(styleSet.modifiers).containsExactlyInAnyOrder(CrossedOut, Blink)
        assertThat(result.modifiers).containsExactlyInAnyOrder(CrossedOut)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(CrossedOut, Blink)

        val result = styleSet.withModifiers(Blink, CrossedOut)

        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
        assertThat(styleSet.modifiers).containsExactlyInAnyOrder(CrossedOut, Blink)
        assertThat(result.modifiers).containsExactlyInAnyOrder(CrossedOut, Blink)
    }

    @Test
    fun shouldNotHaveModifiersByDefault() {
        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
    }

    @Test
    fun shouldGenerateEqualDefaults() {
        assertThat(StyleSet.defaultStyle()).isEqualTo(StyleSet.defaultStyle())
    }

}
