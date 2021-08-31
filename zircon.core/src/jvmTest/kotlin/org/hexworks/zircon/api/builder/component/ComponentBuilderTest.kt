package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.border
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.SimpleModifiers.VerticalFlip
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.ShadowDecorationRenderer
import org.junit.Test

abstract class ComponentBuilderTest<T : Component, U : BaseComponentBuilder<T, U>> {

    abstract val target: BaseComponentBuilder<T, U>

    abstract fun setUp()

    @Test
    open fun test() {
        target.withAlignment(positionalAlignment(POSITION_4X5))
    }

    @Test
    open fun shouldProperlySetPosition() {
        target.withAlignment(positionalAlignment(POSITION_4X5))

        assertThat(target.position).isEqualTo(POSITION_4X5)
    }

    @Test
    open fun shouldProperlySetSize() {
        target.withPreferredSize(SIZE_5X6)

        assertThat(target.size).isEqualTo(SIZE_5X6)
    }

    @Test
    open fun shouldProperlySetComponentStyleSet() {
        target.withComponentStyleSet(COMPONENT_STYLE_SET)

        assertThat(target.componentStyleSet).isEqualTo(COMPONENT_STYLE_SET)
    }

    @Test
    open fun shouldProperlyApplyTitle() {
        target.withDecorations(box(title = TITLE_FOO))

        assertThat(target.title).isEqualTo(TITLE_FOO)
    }

    @Test
    open fun shouldProperlyApplyTileset() {
        target.withTileset(TILESET_ROGUE_YUN)

        assertThat(target.tileset).isEqualTo(TILESET_ROGUE_YUN)
    }

    @Test
    open fun shouldProperlyApplyBoxType() {
        target.withDecorations(box(boxType = BOX_TYPE_DOUBLE))

        assertThat(
            target.decorations
                .filterIsInstance<BoxDecorationRenderer>().first().boxType
        ).isEqualTo(BOX_TYPE_DOUBLE)
    }

    @Test
    open fun shouldProperlyApplyWrappedWithBox() {
        target.withDecorations(box())

        assertThat(
            target.decorations
                .filterIsInstance<BoxDecorationRenderer>()
        ).hasSize(1)
    }

    @Test
    open fun shouldProperlyApplyWrappedWithShadow() {
        target.withDecorations(shadow())

        assertThat(
            target.decorations
                .filterIsInstance<ShadowDecorationRenderer>()
        ).hasSize(1)
    }

    @Test
    open fun shouldProperlyApplyDecorationRenderers() {
        target.withDecorations(*DECORATION_RENDERERS)

        assertThat(target.decorations).containsExactlyElementsOf(DECORATION_RENDERERS.toMutableList())
    }

    companion object {
        val POSITION_4X5 = Position.create(4, 5)
        val SIZE_5X6 = Size.create(5, 6)
        val COMPONENT_STYLE_SET = ComponentStyleSetBuilder.newBuilder()
            .withDefaultStyle(
                StyleSetBuilder.newBuilder()
                    .withBackgroundColor(GREEN)
                    .withForegroundColor(RED)
                    .withModifiers(VerticalFlip)
                    .build()
            )
            .build()
        const val TITLE_FOO = "FOO"
        val TILESET_ROGUE_YUN = CP437TilesetResources.rogueYun16x16()
        val BOX_TYPE_DOUBLE = BoxType.DOUBLE
        val DECORATION_RENDERERS = listOf(border()).toTypedArray()

    }
}
