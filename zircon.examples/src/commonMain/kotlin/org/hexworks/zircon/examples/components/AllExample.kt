package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.margin
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.horizontalNumberInput
import org.hexworks.zircon.api.builder.component.horizontalSlider
import org.hexworks.zircon.api.builder.component.icon
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.listItem
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.progressBar
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.radioButtonGroup
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.component.verticalNumberInput
import org.hexworks.zircon.api.builder.component.verticalSlider
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.RenderingMode.INTERACTIVE
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** Every built-in component on one screen. */
public val allExample: Example = Example("All") { context ->
    lateinit var loading: ProgressBar
    val root = context.oneColumn { box ->
        box.addComponent(hBox {
            name = "Columns"
            preferredSize = box.contentSize
            // The interactions column needs the room; the content column does not.
            val leftSize = Size(preferredSize.width * 2 / 5, preferredSize.height)
            val columnSize = Size(preferredSize.width - leftSize.width, preferredSize.height)
            vBox {
                val column = this
                name = "Left Column"
                preferredSize = leftSize
                decorations { +box(BoxType.TOP_BOTTOM_DOUBLE, "Content") }
                spacing = 1
                header {
                    +"TODOs"
                    decorations { +margin(1, 0, 0, 0) }
                }
                label { +"Foods:" }
                listItem { +"Buy ice cream" }
                listItem { +"Make dinner" }
                paragraph {
                    preferredSize = Size((leftSize.width - 2), 3)
                    +"All this stuff has to be done soon, or otherwise I'll stay hungry."
                }
                label { +"${Symbols.ARROW_DOWN} And this is an icon" }
                icon {
                    iconTile = characterTile { character = Symbols.FACE_WHITE }
                }
                vBox {
                    name = "Text Box"
                    preferredSize = Size((leftSize.width - 2) - 5, 5)
                    decorations {
                        +box()
                        +shadow()
                    }
                    header { +"Text Box!" }
                    paragraph { +"Paragraph text." }
                }
                loading = progressBar {
                    decorations { +box(title = "Loading...") }
                    numberOfSteps = 10
                    range = 100
                    displayPercentValueOfProgress = true
                }
            }
            vBox {
                name = "Right Column"
                preferredSize = columnSize
                decorations { +box(BoxType.TOP_BOTTOM_DOUBLE, "Interactions") }
                spacing = 1
                hBox {
                    spacing = 1
                    decorations { +margin(1, 0, 0, 0) }
                    hBox {
                        decorations { +box(title = "Only numbers") }
                        spacing = 1
                        verticalNumberInput {
                            initialValue = 5
                            minValue = 1
                            maxValue = 10
                            decorations {
                                +box()
                                +margin(1)
                            }
                        }
                        horizontalNumberInput {
                            initialValue = 5
                            minValue = 1
                            maxValue = 1000
                            decorations {
                                +box()
                                +margin(1)
                            }
                        }
                    }
                    vBox {
                        name = "Radio box"
                        decorations { +box(title = "Plans for tonight") }
                        val a = radioButton { +"Sleep"; key = "a" }
                        val b = radioButton { +"Go to the bar"; key = "b" }
                        val c = radioButton { +"Visit a friend"; key = "c" }
                        val d = radioButton { +"Count all the clouds"; key = "d" }
                        radioButtonGroup {
                            radioButtons = listOf(a, b, c, d)
                        }
                    }
                }
                hBox {
                    spacing = 1
                    hBox {
                        spacing = 1
                        decorations { +box(title = "Sliders") }
                        verticalSlider {
                            minValue = 1
                            maxValue = 100
                            numberOfSteps = 3
                            decorations {
                                +box()
                                +margin(1)
                            }
                        }
                        horizontalSlider {
                            minValue = 1
                            maxValue = 100
                            numberOfSteps = 4
                            decorations {
                                +box()
                                +margin(1)
                            }
                        }
                    }
                    vBox {
                        name = "Chores"
                        decorations { +box(title = "Chores") }
                        checkBox { +"Hoovering" }
                        checkBox { +"Mopping" }
                        checkBox { +"Start the dishwasher" }
                        checkBox { +"Take down the trash" }
                    }
                }
                hBox {
                    spacing = 1
                    textArea {
                        decorations { +box(title = "Description") }
                        preferredSize = Size(17, 5)
                    }
                    vBox {
                        decorations { +box(title = "Settings") }
                        toggleButton { +"Mipmapping" }
                        toggleButton { +"Antialias" }
                        toggleButton { +"Motion blur" }
                    }
                }
                hBox {
                    spacing = 1
                    label { +"Delete everything?" }
                    button {
                        +"Sure"
                        decorations { +box(boxType = BoxType.DOUBLE, renderingMode = INTERACTIVE) }
                    }
                    button {
                        +"Yes"
                        decorations { +box(boxType = BoxType.DOUBLE, renderingMode = INTERACTIVE) }
                    }
                }
            }
        })
    }
    loading.progress = 1.0
    var frames = 0
    ExampleView(root, onFrame = {
        // The old example advanced the bar from a thread every 1.5 s; here every 90 frames.
        if (++frames % 90 == 0 && loading.progress < 100) loading.progress++
    })
}
