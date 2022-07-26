package org.hexworks.zircon.examples.components

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.orElse
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.base.OneColumnComponentExample

object DataBindingExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        DataBindingExample.show("Data Binding Example")
    }

    override fun build(box: VBox) {
        box + buildHbox {
            preferredSize = box.contentSize
            val columnSize = preferredSize.withWidth(preferredSize.width / 2)

            vbox {
                name = "Left Column"
                preferredSize = columnSize

                vbox {
                    preferredSize = columnSize.withHeight(4)
                    decoration = box(BoxType.LEFT_RIGHT_DOUBLE, "One-way Binding")

                    val c0 = checkBox { +"Check Me!" }
                    val c1 = checkBox { +"I am bound!" }
                    c1.selectedProperty.updateFrom(c0.selectedProperty)
                }

                vbox {
                    preferredSize = columnSize.withHeight(4)
                    decoration = box(BoxType.LEFT_RIGHT_DOUBLE, "Two-way Binding")

                    val c0 = checkBox { +"I am bound!" }
                    val c1 = checkBox { +"I am bound too!" }
                    c1.selectedProperty.bind(c0.selectedProperty)
                }
            }

            vbox {
                name = "Right Column"
                preferredSize = columnSize
                decoration = box(BoxType.LEFT_RIGHT_DOUBLE, "Selection Binding")


                val r0 = radioButton {
                    +"One"
                    key = "One"
                }
                val r1 = radioButton {
                    +"Two"
                    key = "Two"
                }
                val r2 = radioButton {
                    +"Three"
                    key = "Three"
                }
                val label = label {
                    preferredSize = Size.create(10, 1)
                }
                radioButtonGroup {
                    radioButtons = r0 + r1 + r2
                }.apply {
                    val binding = selectedButtonProperty.bindTransform {
                        it?.text.orElse("")
                    }
                    label.textProperty.updateFrom(binding)
                }
            }

        }
    }
}

