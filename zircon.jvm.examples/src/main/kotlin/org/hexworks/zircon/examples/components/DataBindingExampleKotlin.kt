package org.hexworks.zircon.examples.components

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.checkBox
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.radioButton
import org.hexworks.zircon.api.Components.radioButtonGroup
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin

class DataBindingExampleKotlin : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DataBindingExampleKotlin().show("Data Binding Example")
        }
    }

    override fun build(box: VBox) {
        val columns = hbox()
                .withSize(box.contentSize)
                .build()
        box.addComponents(columns)

        val columnSize = columns.contentSize.withWidth(columns.width / 2)

        val leftColumn = vbox()
                .withSize(columnSize)
                .build()

        val rightColumn = vbox()
                .withSize(columnSize)
                .build()

        vbox()
                .withSize(columnSize.withHeight(4))
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "One-way Binding"))
                .build().apply {
                    leftColumn.addComponent(this)
                    val checkBox0 = checkBox().withText("Check Me!").build()
                    val checkBox1 = checkBox().withText("I am bound!").build()
                    checkBox1.selectedProperty.updateFrom(checkBox0.selectedProperty)
                    addComponents(checkBox0, checkBox1)
                }

        vbox()
                .withSize(columnSize.withHeight(4))
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "Two-way Binding"))
                .build().apply {
                    leftColumn.addComponent(this)
                    val checkBox2 = checkBox().withText("I am bound!").build()
                    val checkBox3 = checkBox().withText("I am bound too!").build()
                    checkBox2.selectedProperty.bind(checkBox3.selectedProperty)
                    addComponents(checkBox2, checkBox3)
                }


        // right column

        vbox()
                .withSize(columnSize.withHeight(6))
                .withDecorations(box(BoxType.LEFT_RIGHT_DOUBLE, "Selection Binding"))
                .build().apply {
                    rightColumn.addComponent(this)

                    val radioButtonGroup = radioButtonGroup()
                            .build()

                    val label = label()
                            .withText("")
                            .withSize(10, 1)
                            .build()

                    val radio0 = radioButton().withText("One").withKey("One").build()
                    val radio1 = radioButton().withText("Two").withKey("Two").build()
                    val radio2 = radioButton().withText("Three").withKey("Three").build()

                    radioButtonGroup.addComponents(radio0, radio1, radio2)

                    val binding = radioButtonGroup.selectedButtonProperty.bindTransform {
                        it.map { radio -> radio.text }.orElse("")
                    }

                    label.textProperty.updateFrom(binding)

                    addComponents(radio0, radio1, radio2, label)
                }


        columns.addComponents(leftColumn, rightColumn)
    }
}
