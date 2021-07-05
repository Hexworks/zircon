package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.border
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.ANSITileColor.*
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.*
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.header
import org.hexworks.zircon.api.dsl.component.panel
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.api.modifier.Border
import org.hexworks.zircon.api.modifier.BorderPosition
import org.hexworks.zircon.api.modifier.BorderPosition.*
import org.hexworks.zircon.examples.base.TwoColumnComponentExampleKotlin
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

class MarginExampleKotlin : TwoColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            MarginExampleKotlin().show("Margin Example")
        }
    }

    override fun build(box: VBox) {
        box.addComponent(buildVbox {

            val panelWidth = 26
            val panelHeight = 8

            header { +"Panels with margin" }

            panel {
                preferredSize = Size.create(panelWidth, panelHeight)
                decoration = border(Border.newBuilder()
                        .withBorderColor(BRIGHT_GREEN)
                        .withBorderPositions(TOP, RIGHT, LEFT)
                        .build(), INTERACTIVE)
                componentRenderer = NoOpComponentRenderer()

                panel {
                    preferredSize = Size.create(panelWidth, panelHeight)
                    decorations = box(title = "Margin 1") + margin(1)
                }
            }

            panel {
                preferredSize = Size.create(panelWidth, panelHeight)
                decoration = border(Border.newBuilder()
                        .withBorderColor(BRIGHT_GREEN)
                        .build(), INTERACTIVE)
                componentRenderer = NoOpComponentRenderer()

                panel {
                    preferredSize = Size.create(panelWidth, panelHeight)
                    decorations = box(title = "Margin 1, 2") + margin(1, 2)
                }
            }

            panel {
                preferredSize = Size.create(panelWidth, panelHeight)
                decoration = border(Border.newBuilder()
                        .withBorderColor(BRIGHT_GREEN)
                        .withBorderPositions(BOTTOM, RIGHT, LEFT)
                        .build(), INTERACTIVE)
                componentRenderer = NoOpComponentRenderer()

                panel {
                    preferredSize = Size.create(panelWidth, panelHeight)
                    decorations = box(title = "Margin 0, 1, 2, 3") + margin(0, 1, 2, 3)
                }
            }
        })
    }
}
