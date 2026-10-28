package org.hexworks.zircon.internal.renderer

import org.hexworks.zircon.api.data.Size
import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.convertCharacterTilesToString
import kotlin.test.Test

class TestRendererTest {

    @Test
    fun tinyExample() {
        val graphics = tileGraphics {
            size = Size(3, 1)
        }
        val testRenderer = TestRenderer(AppConfig.defaultAppConfig(), graphics).apply {
            withComponentContainer {
                addComponent(vBox {
                    preferredSize = Size(3, 1)
                    paragraph {
                        +"Foo"
                    }
                })
            }
        }
        testRenderer.render("null")
        graphics.convertCharacterTilesToString() shouldBe "Foo"
    }

    @Test
    fun rendersAsExpected() {
        val text = "Hello Zircon"
        val size = Size(text.length + 2, 4)
        val graphics = tileGraphics {
            this.size = size;
        }
        val testRenderer = TestRenderer(AppConfig.defaultAppConfig(), graphics).apply {
            withComponentContainer {
                addComponent(
                    vBox {
                        preferredSize = size
                        decorations {
                            +box(boxType = BoxType.TOP_BOTTOM_DOUBLE)
                        }
                        paragraph {
                            +text
                        }
                    }
                )
            }
        }
        testRenderer.render("")
        graphics.convertCharacterTilesToString() shouldBe """
            ╒════════════╕
            │Hello Zircon│
            │            │
            ╘════════════╛
        """.trimIndent()
    }
}
