package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.checkBox
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin

class DataBindingExampleKotlin : OneColumnComponentExampleKotlin() {

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

        val oneWayBinding = vbox()
                .withSize(columnSize.withHeight(4))
                .withDecorations(box(BoxType.SINGLE, "One-way Binding"))
                .build()
        leftColumn.addComponent(oneWayBinding)

        val check0 = checkBox().withText("Check Me!").build()
        val check1 = checkBox().withText("I am bound!").build()
        check1.selectedProperty.updateFrom(check0.selectedProperty)

        oneWayBinding.addComponents(check0, check1)

        columns.addComponents(leftColumn, rightColumn)
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            DataBindingExampleKotlin().show("Data Binding Example")
        }
    }
}