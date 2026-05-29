package org.hexworks.zircon.api.builder.component

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.modifier.SimpleModifiers.Blink
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.ShadowDecorationRenderer
import kotlin.test.Test

abstract class JvmComponentBuilderTest<T : Component> {

    abstract val target: BaseComponentBuilder<T>

    abstract fun setUp()

    @Test
    open fun test() {
        target.alignment = positionalAlignment(POSITION_4X5)
    }

    @Test
    open fun shouldProperlySetPosition() {
        target.alignment = positionalAlignment(POSITION_4X5)

        target.position shouldBe POSITION_4X5
    }

    @Test
    open fun shouldProperlySetSize() {
        target.preferredSize = SIZE_5X6

        target.size shouldBe SIZE_5X6
    }

    @Test
    open fun shouldProperlySetComponentStyleSet() {
        target.componentStyleSet = COMPONENT_STYLE_SET

        target.componentStyleSet shouldBe COMPONENT_STYLE_SET
    }

    @Test
    open fun shouldProperlyApplyTitle() {
        target.decorations {
            +box(title = TITLE_FOO)
        }

        target.title shouldBe TITLE_FOO
    }

    @Test
    open fun shouldProperlyApplyTileset() {
        target.tileset = TILESET_ROGUE_YUN

        target.tileset shouldBe TILESET_ROGUE_YUN
    }

    @Test
    open fun shouldProperlyApplyBoxType() {
        target.decorations {
            +box(boxType = BOX_TYPE_DOUBLE)
        }

        target.decorationRenderers
            .filterIsInstance<BoxDecorationRenderer>().first().boxType shouldBe BOX_TYPE_DOUBLE
    }

    @Test
    open fun shouldProperlyApplyWrappedWithBox() {
        target.decorations {
            +box()
        }

        target.decorationRenderers
            .filterIsInstance<BoxDecorationRenderer>() shouldHaveSize 1
    }

    @Test
    open fun shouldProperlyApplyWrappedWithShadow() {
        target.decorations {
            +shadow()
        }

        target.decorationRenderers
            .filterIsInstance<ShadowDecorationRenderer>() shouldHaveSize 1
    }

    companion object {
        val POSITION_4X5 = Position.create(4, 5)
        val SIZE_5X6 = Size.create(5, 6)
        val COMPONENT_STYLE_SET = componentStyleSet {
            defaultStyle = styleSet {
                backgroundColor = DefaultAnsiPalette[ANSIColor.GREEN]
                foregroundColor = DefaultAnsiPalette[ANSIColor.RED]
                modifiers = setOf(Blink)
            }
        }
        const val TITLE_FOO = "FOO"
        val TILESET_ROGUE_YUN = CP437TilesetResources.rogueYun16x16()
        val BOX_TYPE_DOUBLE = BoxType.DOUBLE
    }
}
