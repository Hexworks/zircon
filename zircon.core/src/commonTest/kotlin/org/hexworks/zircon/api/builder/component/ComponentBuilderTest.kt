package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentAlignments.positionalAlignment
import org.hexworks.zircon.api.ComponentDecorations.border
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Modifiers
import org.hexworks.zircon.api.builder.graphics.styleSet
import org.hexworks.zircon.api.color.ANSITileColor.GREEN
import org.hexworks.zircon.api.color.ANSITileColor.RED
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.BaseComponentBuilder
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.renderer.decoration.BoxDecorationRenderer
import org.hexworks.zircon.internal.component.renderer.decoration.ShadowDecorationRenderer
import kotlin.test.Test
import kotlin.test.assertEquals

abstract class ComponentBuilderTest<T : Component, U : BaseComponentBuilder<T>> {

    abstract val target: BaseComponentBuilder<T>

    abstract fun setUp()

    @Test
    open fun test() {
        target.alignment = positionalAlignment(POSITION_4X5)
    }

    @Test
    open fun shouldProperlySetPosition() {
        target.alignment = positionalAlignment(POSITION_4X5)

        assertEquals(POSITION_4X5, target.position)
    }

    @Test
    open fun shouldProperlySetSize() {
        target.preferredSize = SIZE_5X6

        assertEquals(SIZE_5X6, target.size)
    }

    @Test
    open fun shouldProperlySetComponentStyleSet() {
        target.componentStyleSet = COMPONENT_STYLE_SET

        assertEquals(COMPONENT_STYLE_SET, target.componentStyleSet)
    }

    @Test
    open fun shouldProperlyApplyTitle() {
        target.decorations {
            +box(title = TITLE_FOO)
        }

        assertEquals(TITLE_FOO, target.title)
    }

    @Test
    open fun shouldProperlyApplyTileset() {
        target.tileset = TILESET_ROGUE_YUN

        assertEquals(TILESET_ROGUE_YUN, target.tileset)
    }

    @Test
    open fun shouldProperlyApplyBoxType() {
        target.decorations {
            +box(boxType = BOX_TYPE_DOUBLE)
        }

        assertEquals(
            BOX_TYPE_DOUBLE, target.decorationRenderers
                .filterIsInstance<BoxDecorationRenderer>().first().boxType
        )
    }

    @Test
    open fun shouldProperlyApplyWrappedWithBox() {
        target.decorations {
            +box()
        }

        assertEquals(
            1, target.decorationRenderers
                .filterIsInstance<BoxDecorationRenderer>().size
        )
    }

    @Test
    open fun shouldProperlyApplyWrappedWithShadow() {
        target.decorations {
            +shadow()
        }

        assertEquals(
            1, target.decorationRenderers
                .filterIsInstance<ShadowDecorationRenderer>().size
        )
    }

    @Test
    open fun shouldProperlyApplyDecorationRenderers() {
        target.decorations {
            DECORATION_RENDERERS.forEach {
                +it
            }
        }

        assertEquals(DECORATION_RENDERERS.toMutableList(), target.decorationRenderers)
    }

    companion object {
        val POSITION_4X5 = Position.create(4, 5)
        val SIZE_5X6 = Size.create(5, 6)
        val COMPONENT_STYLE_SET = componentStyleSet {
            defaultStyle = styleSet {
                backgroundColor = GREEN
                foregroundColor = RED
                modifiers = setOf(Modifiers.crossedOut())
            }
        }
        const val TITLE_FOO = "FOO"
        val TILESET_ROGUE_YUN = CP437TilesetResources.rogueYun16x16()
        val BOX_TYPE_DOUBLE = BoxType.DOUBLE
        val DECORATION_RENDERERS = listOf(border()).toTypedArray()

    }
}
