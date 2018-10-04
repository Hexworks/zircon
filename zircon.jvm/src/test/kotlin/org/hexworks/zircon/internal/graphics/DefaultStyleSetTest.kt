package org.hexworks.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.SimpleModifiers.*
import org.hexworks.zircon.api.Modifiers
import org.junit.Test

class DefaultStyleSetTest {

    @Test
    fun shouldBuildProperCacheKey() {
        val result = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITileColor.WHITE)
                .foregroundColor(TileColor.fromString("#aabbcc"))
                .modifiers(Modifiers.crossedOut(), BorderBuilder.newBuilder().borderPositions(BorderPosition.TOP).build())
                .build().generateCacheKey()
        assertThat(result).isEqualTo("StyleSet(fg=TextColor(r=170,g=187,b=204,a=255),bg=TextColor(r=170,g=170,b=170,a=255),m=[Modifier.CrossedOut,Modifier.Border(t=SOLID,bp=[TOP])])")
    }

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(VerticalFlip)

        val result = styleSet.withAddedModifiers(HorizontalFlip)

        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
        assertThat(styleSet.modifiers).containsExactlyInAnyOrder(VerticalFlip)
        assertThat(result.modifiers).containsExactlyInAnyOrder(VerticalFlip, HorizontalFlip)
    }

    @Test
    fun shouldRemoveModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(VerticalFlip, HorizontalFlip)

        val result = styleSet.withRemovedModifiers(HorizontalFlip)

        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
        assertThat(styleSet.modifiers).containsExactlyInAnyOrder(VerticalFlip, HorizontalFlip)
        assertThat(result.modifiers).containsExactlyInAnyOrder(VerticalFlip)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(VerticalFlip, HorizontalFlip)

        val result = styleSet.withModifiers(HorizontalFlip, CrossedOut)

        assertThat(StyleSet.defaultStyle().modifiers).isEmpty()
        assertThat(styleSet.modifiers).containsExactlyInAnyOrder(VerticalFlip, HorizontalFlip)
        assertThat(result.modifiers).containsExactlyInAnyOrder(CrossedOut, HorizontalFlip)
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
