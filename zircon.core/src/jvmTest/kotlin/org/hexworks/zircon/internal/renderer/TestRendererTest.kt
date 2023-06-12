package org.hexworks.zircon.internal.renderer

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.builder.component.buildVbox
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.data.size
import org.hexworks.zircon.api.builder.graphics.tileGraphics
import org.hexworks.zircon.api.builder.graphics.withSize
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.builder.base.withPreferredContentSize
import org.hexworks.zircon.api.component.builder.base.withPreferredSize
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.convertCharacterTilesToString
import org.junit.Test

class TestRendererTest {

    @Test
    fun tinyExample() {
        val graphics = tileGraphics {
            withSize {
                width = 3
                height = 1
            }
        }
        val testRenderer = TestRenderer(AppConfig.defaultAppConfig(), graphics).apply {
            withComponentContainer {
                addComponent(buildVbox {
                    withPreferredSize {
                        width = 3
                        height = 1
                    }
                    paragraph {
                        +"Foo"
                    }
                })
            }
        }
        testRenderer.render("null")
        assertThat(graphics.convertCharacterTilesToString()).isEqualTo("Foo")
    }

    @Test
    fun rendersAsExpected() {
        val text = "Hello Zircon"
        val size = size {
                width = text.length + 2
                height = 4
        }
        val graphics = tileGraphics {
            this.size = size;
        }
        val testRenderer = TestRenderer(AppConfig.defaultAppConfig(), graphics).apply {
            withComponentContainer {
                addComponent(
                    buildVbox {
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
