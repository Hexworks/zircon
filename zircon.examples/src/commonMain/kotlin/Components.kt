import korlibs.io.async.delay
import korlibs.io.async.launch
import korlibs.time.seconds
import kotlinx.coroutines.Dispatchers
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.application.Application
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.dsl.plus
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.graphics.BoxType

val DEFAULT_TILESET = CP437TilesetResources.rexPaint16x16()

suspend fun Application.components() = also {
    val screen = tileGrid.toScreen()

    val progressProp = 0.0.toProperty()

    var progress: Double by progressProp.asDelegate()

    launch(Dispatchers.Default) {
        while (progress < 100) {
            delay(2.seconds)
            progress++
        }
    }

    screen.addComponent(buildHbox {
        name = "Columns"
        preferredSize = screen.size.withRelativeHeight(-1)
        val columnSize = preferredSize.withWidth(preferredSize.width / 2)

        vbox {
            val column = this
            name = "Left Column"
            preferredSize = columnSize
            decorations = listOf(box(BoxType.TOP_BOTTOM_DOUBLE, "Content"))
            spacing = 1

            header {
                +"TODOs"
                decoration = ComponentDecorations.margin(1, 0, 0, 0)
            }
            label { +"Foods:" }
            listItem { +"Buy ice cream" }
            listItem { +"Make dinner" }
            paragraph {
                preferredSize = Size.create(column.contentWidth, 3)
                +"All this stuff has to be done soon, or otherwise I'll stay hungry."
            }
//            if (DEFAULT_TILESET.size.width == 16) {
//                label { +"${Symbols.ARROW_DOWN} And this is an icon" }
//                icon {
//                    iconTile = Tile.newBuilder()
//                        .withTileset(GraphicalTilesetResources.nethack16x16())
//                        .withName("Plate mail")
//                        .buildGraphicalTile()
//                    tileset = GraphicalTilesetResources.nethack16x16()
//                }
//            }
            textBox(column.contentWidth - 5) {
                name = "Text Box"
                decorations = box() + ComponentDecorations.shadow()

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
                progressProperty.updateFrom(progressProp)
            }
        }

        vbox {
            name = "Right Column"
            preferredSize = columnSize
            decoration = box(BoxType.TOP_BOTTOM_DOUBLE, "Interactions")
            spacing = 1

            hbox {
                spacing = 1
                decoration = ComponentDecorations.margin(1, 0, 0, 0)

                hbox {
                    decoration = box(title = "Only numbers")
                    spacing = 1
                    verticalNumberInput {
                        initialValue = 5
                        minValue = 1
                        maxValue = 10
                        decorations = box() + ComponentDecorations.margin(1)
                    }
                    horizontalNumberInput {
                        initialValue = 5
                        minValue = 1
                        maxValue = 1000
                        decorations = box() + ComponentDecorations.margin(1)
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
                        decorations = box() + ComponentDecorations.margin(1)
                    }
                    horizontalSlider {
                        minValue = 1
                        maxValue = 100
                        numberOfSteps = 4
                        decorations = box() + ComponentDecorations.margin(1)
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
                        renderingMode = ComponentDecorationRenderer.RenderingMode.INTERACTIVE
                    ) + ComponentDecorations.margin(1)
                    onActivated {
                        println("Deleting everything...")
                    }
                }
                button {
                    +"Yes"
                    decorations = box(
                        boxType = BoxType.DOUBLE,
                        renderingMode = ComponentDecorationRenderer.RenderingMode.INTERACTIVE
                    ) + ComponentDecorations.margin(1)
                    onActivated {
                        println("Deleting everything...")
                    }
                }
            }
        }
    })
    screen.display()
}