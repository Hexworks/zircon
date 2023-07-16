package org.hexworks.zircon.internal.component.renderer.decoration

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.convertCharacterTilesToString
import org.hexworks.zircon.internal.graphics.Renderable
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class BoxDecorationRendererTest(
    @Suppress("unused") private val testTitle: String, // used by Parameterized for display in IntelliJ/terminal
    private val decorator: ComponentDecorationRenderer,
    private val expected: String
) {
    @Test
    fun renderTest() {
        val target = Components.panel()
            .withTileset(CP437TilesetResources.rexPaint20x20())
            .withDecorations(decorator)
            .build()

        val graphics = DrawSurfaces.tileGraphicsBuilder().withSize(Size.create(12, 4)).build()
        (target as Renderable).render(graphics)
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(expected.trimIndent())
    }

    companion object {
        @Parameterized.Parameters(name = "{index}: {0}")
        @JvmStatic
        fun data() = listOf(
            arrayOf(
                "Default parameters", box(), """
                ┌──────────┐
                │          │
                │          │
                └──────────┘
            """
            ),
            arrayOf(
                "With title", box(title = "Foo"), """
                ┌┤Foo├─────┐
                │          │
                │          │
                └──────────┘
            """
            ),
            arrayOf(
                "With title, right-aligned", box(title = "Foo", titleAlignment = TOP_RIGHT), """
                ┌─────┤Foo├┐
                │          │
                │          │
                └──────────┘
            """
            ),
            // Titlebar is even-sized width, but title is odd-sized, so we're off by half, rounding to the left.
            arrayOf(
                "With title, center-aligned, approx center", box(title = "Foo", titleAlignment = TOP_CENTER), """
                ┌──┤Foo├───┐
                │          │
                │          │
                └──────────┘
            """
            ),
            arrayOf(
                "With title, center-aligned, exact center", box(title = "Food", titleAlignment = TOP_CENTER), """
                ┌──┤Food├──┐
                │          │
                │          │
                └──────────┘
            """
            ),
            arrayOf(
                "With title, bottom left-aligned", box(title = "Foo", titleAlignment = BOTTOM_LEFT), """
                ┌──────────┐
                │          │
                │          │
                └┤Foo├─────┘
            """
            ),
            arrayOf(
                "With title, bottom right-aligned", box(title = "Foo", titleAlignment = BOTTOM_RIGHT), """
                ┌──────────┐
                │          │
                │          │
                └─────┤Foo├┘
            """
            ),
            arrayOf(
                "With title, bottom center-aligned", box(title = "Foo", titleAlignment = BOTTOM_CENTER), """
                ┌──────────┐
                │          │
                │          │
                └──┤Foo├───┘
            """
            )
        )
    }
}
