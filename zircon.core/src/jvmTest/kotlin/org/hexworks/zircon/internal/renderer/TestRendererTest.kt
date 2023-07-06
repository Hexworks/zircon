package org.hexworks.zircon.internal.renderer

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.DrawSurfaces
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.convertCharacterTilesToString
import org.junit.Test

class TestRendererTest {
    @Test
    fun tinyExample() {
        val graphics = DrawSurfaces.tileGraphicsBuilder().withSize(Size.create(3, 1)).build()
        val testRenderer = TestRenderer(AppConfig.defaultConfiguration(), graphics).apply {
            withComponentContainer {
                addComponent(Components.textBox(3).addParagraph("Foo").build())
            }
        }
        testRenderer.render("null")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo("Foo")
    }

    @Test
    fun rendersAsExpected() {
        val text = "Hello Zircon"
        val graphics = DrawSurfaces.tileGraphicsBuilder().withSize(Size.create(text.length + 2, 4)).build()
        val testRenderer = TestRenderer(AppConfig.defaultConfiguration(), graphics).apply {
            withComponentContainer {
                addComponent(
                    Components.textBox(text.length)
                        .withDecorations(box(boxType = BoxType.TOP_BOTTOM_DOUBLE))
                        .addParagraph(text, withNewLine = false)
                        .build()
                )
            }
        }
        testRenderer.render("")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo(
            """
            ╒════════════╕
            │Hello Zircon│
            │            │
            ╘════════════╛
        """.trimIndent()
        )
    }
}
