package org.hexworks.zircon.api.builder.component

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.builder.graphics.StyleSetBuilder
import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.ComponentBuilder
import org.hexworks.zircon.api.component.renderer.impl.BorderDecorationRenderer
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.SimpleModifiers.VerticalFlip
import org.hexworks.zircon.api.resource.BuiltInCP437TilesetResource
import org.junit.Test

abstract class ComponentBuilderTest<T : Component, U : ComponentBuilder<T, U>> {

    abstract val target: ComponentBuilder<T, U>

    abstract fun setUp()

    @Test
    open fun shouldProperlySetPosition() {
        target.withPosition(POSITION_4X5)

        assertThat(target.position).isEqualTo(POSITION_4X5)
    }

    @Test
    open fun shouldProperlySetSize() {
        target.withSize(SIZE_2X3)

        assertThat(target.size).isEqualTo(SIZE_2X3)
    }

    @Test
    open fun shouldProperlySetComponentStyleSet() {
        target.withComponentStyleSet(COMPONENT_STYLE_SET)

        assertThat(target.componentStyleSet).isEqualTo(COMPONENT_STYLE_SET)
    }

    @Test
    open fun shouldProperlyApplyTitle() {
        target.withTitle(TITLE_FOO)

        assertThat(target.title).isEqualTo(TITLE_FOO)
    }

    @Test
    open fun shouldProperlyApplyTileset() {
        target.withTileset(TILESET_ROGUE_YUN)

        assertThat(target.tileset).isEqualTo(TILESET_ROGUE_YUN)
    }

    @Test
    open fun shouldProperlyApplyBoxType() {
        target.withBoxType(BOX_TYPE_DOUBLE)

        assertThat(target.boxType).isEqualTo(BOX_TYPE_DOUBLE)
    }

    @Test
    open fun shouldProperlyApplyWrappedWithBox() {
        target.wrapWithBox(WRAPPED_WITH_BOX)

        assertThat(target.wrappedWithBox).isTrue()
    }

    @Test
    open fun shouldProperlyApplyWrappedWithShadow() {
        target.wrapWithShadow(WRAPPED_WITH_SHADOW)

        assertThat(target.wrappedWithShadow).isTrue()
    }

    @Test
    open fun shouldProperlyApplyDecorationRenderers() {
        target.withDecorationRenderers(*DECORATION_RENDERERS)

        assertThat(target.decorationRenderers).containsExactlyElementsOf(DECORATION_RENDERERS.toMutableList())
    }

    companion object {
        val POSITION_4X5 = Position.create(4, 5)
        val SIZE_2X3 = Size.create(2, 3)
        val COMPONENT_STYLE_SET = ComponentStyleSetBuilder.newBuilder()
                .withDefaultStyle(StyleSetBuilder.newBuilder()
                        .withBackgroundColor(GREEN)
                        .withForegroundColor(RED)
                        .withModifiers(VerticalFlip)
                        .build())
                .build()
        const val TITLE_FOO = "FOO"
        val TILESET_ROGUE_YUN = BuiltInCP437TilesetResource.ROGUE_YUN_16X16
        val BOX_TYPE_DOUBLE = BoxType.DOUBLE
        const val WRAPPED_WITH_BOX = true
        const val WRAPPED_WITH_SHADOW = true
        val DECORATION_RENDERERS = listOf(BorderDecorationRenderer(BorderBuilder.newBuilder().build()))
                .toTypedArray()

    }
}
