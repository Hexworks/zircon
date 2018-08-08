package org.codetome.zircon.internal.graphics

import org.assertj.core.api.Assertions.assertThat
import org.codetome.zircon.api.builder.graphics.StyleSetBuilder
import org.codetome.zircon.api.builder.modifier.BorderBuilder
import org.codetome.zircon.api.color.ANSITextColor
import org.codetome.zircon.api.color.TextColor
import org.codetome.zircon.api.data.GraphicTile
import org.codetome.zircon.api.graphics.StyleSet
import org.codetome.zircon.api.modifier.BorderPosition
import org.codetome.zircon.api.modifier.SimpleModifiers.*
import org.codetome.zircon.api.interop.Modifiers
import org.junit.Test

class DefaultStyleSetTest {

    @Test
    fun shouldBuildProperCacheKey() {
        val result = StyleSetBuilder.newBuilder()
                .backgroundColor(ANSITextColor.WHITE)
                .foregroundColor(TextColor.fromString("#aabbcc"))
                .modifiers(Modifiers.crossedOut(), BorderBuilder.newBuilder().borderPositions(BorderPosition.TOP).build())
                .build().generateCacheKey()
        assertThat(result).isEqualTo("DefaultStyleSet(foregroundColor=DefaultTextColor(red=170, green=187, blue=204, alpha=255), backgroundColor=WHITE, modifiers=[Modifier.CrossedOut, Border(borderType=SOLID, borderPositions=[TOP])])")
    }

    @Test
    fun shouldAddModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(VerticalFlip)

        val result = styleSet.withAddedModifiers(HorizontalFlip)

        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(VerticalFlip)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(VerticalFlip, HorizontalFlip)
    }

    @Test
    fun shouldRemoveModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(VerticalFlip, HorizontalFlip)

        val result = styleSet.withRemovedModifiers(HorizontalFlip)

        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(VerticalFlip, HorizontalFlip)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(VerticalFlip)
    }

    @Test
    fun shouldSetModifiersProperly() {
        val styleSet = StyleSet.defaultStyle().withAddedModifiers(VerticalFlip, HorizontalFlip)

        val result = styleSet.withModifiers(HorizontalFlip, CrossedOut)

        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
        assertThat(styleSet.getModifiers()).containsExactlyInAnyOrder(VerticalFlip, HorizontalFlip)
        assertThat(result.getModifiers()).containsExactlyInAnyOrder(CrossedOut, HorizontalFlip)
    }

    @Test
    fun shouldNotHaveModifiersByDefault() {
        assertThat(StyleSet.defaultStyle().getModifiers()).isEmpty()
    }

    @Test
    fun shouldGenerateEqualDefaults() {
        assertThat(StyleSet.defaultStyle()).isEqualTo(StyleSet.defaultStyle())
    }

}
