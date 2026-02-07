package org.hexworks.zircon.internal.component.renderer.decoration

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.buildPanel
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.*
import org.hexworks.zircon.convertCharacterTilesToString
import org.hexworks.zircon.internal.graphics.Renderable
import kotlin.test.Test

class BoxDecorationRendererTest {

    @Test
    fun testDefaultParameters() {
        val decorator = box()
        val expected = """
            ┌──────────┐
            │          │
            │          │
            └──────────┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitle() {
        val decorator = box(title = "Foo")
        val expected = """
            ┌┤Foo├─────┐
            │          │
            │          │
            └──────────┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitleRightAligned() {
        val decorator = box(title = "Foo", titleAlignment = TOP_RIGHT)
        val expected = """
            ┌─────┤Foo├┐
            │          │
            │          │
            └──────────┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitleCenterAlignedApproxCenter() {
        // Titlebar is even-sized width, but title is odd-sized, so we're off by half, rounding to the left.
        val decorator = box(title = "Foo", titleAlignment = TOP_CENTER)
        val expected = """
            ┌──┤Foo├───┐
            │          │
            │          │
            └──────────┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitleCenterAlignedExactCenter() {
        val decorator = box(title = "Food", titleAlignment = TOP_CENTER)
        val expected = """
            ┌──┤Food├──┐
            │          │
            │          │
            └──────────┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitleBottomLeftAligned() {
        val decorator = box(title = "Foo", titleAlignment = BOTTOM_LEFT)
        val expected = """
            ┌──────────┐
            │          │
            │          │
            └┤Foo├─────┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitleBottomRightAligned() {
        val decorator = box(title = "Foo", titleAlignment = BOTTOM_RIGHT)
        val expected = """
            ┌──────────┐
            │          │
            │          │
            └─────┤Foo├┘
        """
        runRenderTest(decorator, expected)
    }

    @Test
    fun testWithTitleBottomCenterAligned() {
        val decorator = box(title = "Foo", titleAlignment = BOTTOM_CENTER)
        val expected = """
            ┌──────────┐
            │          │
            │          │
            └──┤Foo├───┘
        """
        runRenderTest(decorator, expected)
    }

    private fun runRenderTest(decorator: org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer, expected: String) {
        val target = buildPanel {
            tileset = CP437TilesetResources.rexPaint20x20()
            decorations {
                +decorator
            }
        }

        val graphics = tileGraphics {
            withSize {
                width = 12
                height = 4
            }
        }
        (target as Renderable).render(graphics)
        graphics.convertCharacterTilesToString() shouldBe expected.trimIndent()
    }
}
