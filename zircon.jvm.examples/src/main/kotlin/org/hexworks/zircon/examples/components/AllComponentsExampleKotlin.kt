package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile.Companion.newBuilder
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.checkBox
import org.hexworks.zircon.api.dsl.component.hbox
import org.hexworks.zircon.api.dsl.component.header
import org.hexworks.zircon.api.dsl.component.horizontalNumberInput
import org.hexworks.zircon.api.dsl.component.horizontalSlider
import org.hexworks.zircon.api.dsl.component.icon
import org.hexworks.zircon.api.dsl.component.label
import org.hexworks.zircon.api.dsl.component.listItem
import org.hexworks.zircon.api.dsl.component.paragraph
import org.hexworks.zircon.api.dsl.component.plus
import org.hexworks.zircon.api.dsl.component.progressBar
import org.hexworks.zircon.api.dsl.component.radioButton
import org.hexworks.zircon.api.dsl.component.radioButtonGroup
import org.hexworks.zircon.api.dsl.component.textBox
import org.hexworks.zircon.api.dsl.component.vbox
import org.hexworks.zircon.api.dsl.component.verticalNumberInput
import org.hexworks.zircon.api.dsl.component.verticalSlider
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.base.DEFAULT_TILESET
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import kotlin.concurrent.thread

class AllComponentsExampleKotlin : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            AllComponentsExampleKotlin().show("All Components Example")
        }
    }

    override fun build(box: VBox) {
        box.addComponent(buildHbox {
            name = "Columns"
            preferredSize = box.contentSize.withRelativeHeight(-1)
            val columnSize = preferredSize.withWidth(preferredSize.width / 2)

            vbox {
                val column = this
                name = "Left Column"
                preferredSize = columnSize
                decoration = box(BoxType.TOP_BOTTOM_DOUBLE, "Content")
                spacing = 1

                header {
                    +"TODOs"
                    decoration = margin(1, 0, 0, 0)
                }
                label { +"Foods:" }
                listItem { +"Buy ice cream" }
                listItem { +"Make dinner" }
                paragraph {
                    preferredSize = Size.create(column.contentWidth, 3)
                    +"All this stuff has to be done soon, or otherwise I'll stay hungry."
                }
                if (DEFAULT_TILESET.size.width == 16) {
                    label { +"${Symbols.ARROW_DOWN} And this is an icon" }
                    icon {
                        iconTile = newBuilder()
                            .withTileset(nethack16x16())
                            .withName("Plate mail")
                            .buildGraphicalTile()
                        tileset = nethack16x16()
                    }
                }
                textBox(column.contentWidth - 5) {
                    name = "Text Box"
                    decorations = box() + shadow()

                    addHeader("Text Box!")

                    paragraph {
                        +"Paragraph text."
                    }
                }
                progressBar {
                    decoration = box(title = "Loading...")
                    numberOfSteps = 10
                    range = 100
                    displayPercentValueOfProgress = true
                }.apply {
                    progress = 1.0
                    thread {
                        try {
                            while (progress < 100) {
                                Thread.sleep(1500)
                                progress++
                            }
                        } catch (e: InterruptedException) {
                            e.printStackTrace()
                        }
                    }
                }
            }

            vbox {
                name = "Right Column"
                preferredSize = columnSize
                decoration = box(BoxType.TOP_BOTTOM_DOUBLE, "Interactions")
                spacing = 1

                hbox {
                    spacing = 1
                    decoration = margin(1, 0, 0, 0)

                    verticalNumberInput {
                        initialValue = 15
                        minValue = 1
                        maxValue = 100
                        decoration = box()
                    }
                    horizontalNumberInput {
                        initialValue = 5
                        minValue = 1
                        maxValue = 100
                        decoration = box()
                    }
                    vbox {
                        name = "Radio box"
                        decoration = box(title = "Tonight")

                        val a = radioButton {
                            +"Stay home?"
                            key = "a"
                        }
                        val b = radioButton {
                            +"Or go out"
                            key = "b"
                        }

                        radioButtonGroup {
                            radioButtons = a + b
                        }
                    }
                }

                hbox {
                    spacing = 1
                    verticalSlider {
                        minValue = 1
                        maxValue = 100
                        numberOfSteps = 3
                        decoration = box()
                    }
                    horizontalSlider {
                        minValue = 1
                        maxValue = 100
                        numberOfSteps = 3
                        decoration = box()
                    }
                    vbox {
                        name = "Chores"
                        decoration = box(title = "Chores")
                        checkBox { +"Hoovering" }
                        checkBox { +"Mopping" }
                    }
                }
            }
        })
    }
}
