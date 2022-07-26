package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile.Companion.newBuilder
import org.hexworks.zircon.api.dsl.component.buildHbox
import org.hexworks.zircon.api.dsl.component.button
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
import org.hexworks.zircon.api.dsl.component.textArea
import org.hexworks.zircon.api.dsl.component.textBox
import org.hexworks.zircon.api.dsl.component.toggleButton
import org.hexworks.zircon.api.dsl.component.vbox
import org.hexworks.zircon.api.dsl.component.verticalNumberInput
import org.hexworks.zircon.api.dsl.component.verticalSlider
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.base.DEFAULT_TILESET
import org.hexworks.zircon.examples.base.OneColumnComponentExample
import kotlin.concurrent.thread
import kotlin.jvm.JvmStatic

object AllComponentsExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        AllComponentsExample.show("All Components Example")
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

                    hbox {
                        decoration = box(title = "Only numbers")
                        spacing = 1
                        verticalNumberInput {
                            initialValue = 5
                            minValue = 1
                            maxValue = 10
                            decorations = box() + margin(1)
                        }
                        horizontalNumberInput {
                            initialValue = 5
                            minValue = 1
                            maxValue = 1000
                            decorations = box() + margin(1)
                        }
                    }
                    vbox {
                        name = "Radio box"
                        decoration = box(title = "Plans for tonight")

                        val a = radioButton {
                            +"Sleep"
                            key = "a"
                        }
                        val b = radioButton {
                            +"Go to the bar"
                            key = "b"
                        }

                        val c = radioButton {
                            +"Visit a friend"
                            key = "c"
                        }

                        val d = radioButton {
                            +"Drink beer"
                            key = "d"
                        }

                        val e = radioButton {
                            +"Don't do anything"
                            key = "e"
                        }

                        val f = radioButton {
                            +"Count all the clouds"
                            key = "f"
                        }

                        val g = radioButton {
                            +"watch movies"
                            key = "g"
                        }

                        radioButtonGroup {
                            radioButtons = a + b + c + d + e + f + g
                        }
                    }
                }

                hbox {
                    spacing = 1
                    hbox {
                        spacing = 1
                        decoration = box(title = "Sliders")
                        verticalSlider {
                            minValue = 1
                            maxValue = 100
                            numberOfSteps = 3
                            decorations = box() + margin(1)
                        }
                        horizontalSlider {
                            minValue = 1
                            maxValue = 100
                            numberOfSteps = 4
                            decorations = box() + margin(1)
                        }
                    }
                    vbox {
                        name = "Chores"
                        decoration = box(title = "Chores")
                        checkBox { +"Hoovering" }
                        checkBox { +"Mopping" }
                        checkBox { +"Start the dishwasher" }
                        checkBox { +"Do the washing up" }
                        checkBox { +"Take down the trash" }
                        checkBox { +"Dusting" }
                        checkBox { +"Buy detergent" }
                        checkBox { +"Clean the toilet" }
                    }
                }

                hbox {
                    spacing = 1

                    textArea {
                        decoration = box(title = "Description")
                        preferredSize = Size.create(17, 8)
                    }

                    vbox {
                        decoration = box(title = "Settings")

                        toggleButton { +"Mipmapping" }
                        toggleButton { +"Antialias" }
                        toggleButton { +"Film grain" }
                        toggleButton { +"Motion blur" }
                        toggleButton { +"Display FPS " }
                        toggleButton { +"God rays" }
                        toggleButton { +"Chromatic aberration " }
                    }
                }

                vbox {
                    header { +"Delete everything?" }
                }
                hbox {
                    spacing = 1

                    button {
                        +"Sure"
                        decorations = box(
                            boxType = BoxType.DOUBLE,
                            renderingMode = INTERACTIVE
                        ) + margin(1)
                        onActivated {
                            println("Deleting everything...")
                        }
                    }
                    button {
                        +"Yes"
                        decorations = box(
                            boxType = BoxType.DOUBLE,
                            renderingMode = INTERACTIVE
                        ) + margin(1)
                        onActivated {
                            println("Deleting everything...")
                        }
                    }
                }
            }
        })
    }
}
