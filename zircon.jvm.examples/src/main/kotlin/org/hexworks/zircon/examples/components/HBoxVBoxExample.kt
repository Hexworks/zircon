package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.base.OneColumnComponentExample

object HBoxVBoxExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        HBoxVBoxExample.show("HBox + VBox Example")
    }

    override fun build(box: VBox) {
        val contentWidth = 46
        val table = vbox()
            .withPreferredSize(contentWidth + 2, 20)
            .withAlignmentWithin(box, ComponentAlignment.CENTER)
            .withDecorations(box(BoxType.SINGLE, "Crew"))
            .build()
        val headerRow = hbox()
            .withPreferredSize(contentWidth, 1)
            .build()
        headerRow.addComponent(header().withText("#").withPreferredSize(4, 1))
        headerRow.addComponent(header().withText("First Name").withPreferredSize(12, 1))
        headerRow.addComponent(header().withText("Last Name").withPreferredSize(12, 1))
        headerRow.addComponent(header().withText("Job").withPreferredSize(12, 1))
        val samLawrey = hbox()
            .withPreferredSize(contentWidth, 1)
            .build()
        samLawrey.addComponent(label().withText("0").withPreferredSize(4, 1))
        samLawrey.addComponent(label().withText("Sam").withPreferredSize(12, 1))
        samLawrey.addComponent(label().withText("Lawrey").withPreferredSize(12, 1))
        samLawrey.addComponent(label().withText("Stoneworker").withPreferredSize(12, 1))
        val janeFisher = hbox()
            .withPreferredSize(contentWidth, 1)
            .build()
        janeFisher.addComponent(label().withText("1").withPreferredSize(4, 1))
        janeFisher.addComponent(label().withText("Jane").withPreferredSize(12, 1))
        janeFisher.addComponent(label().withText("Fisher").withPreferredSize(12, 1))
        janeFisher.addComponent(label().withText("Woodcutter").withPreferredSize(12, 1))
        val johnSmith = hbox()
            .withPreferredSize(contentWidth, 1)
            .build()
        johnSmith.addComponent(label().withText("2").withPreferredSize(4, 1))
        johnSmith.addComponent(label().withText("John").withPreferredSize(12, 1))
        johnSmith.addComponent(label().withText("Smith").withPreferredSize(12, 1))
        johnSmith.addComponent(label().withText("Mason").withPreferredSize(12, 1))
        val steveThrush = hbox()
            .withPreferredSize(contentWidth, 1)
            .build()
        steveThrush.addComponent(label().withText("3").withPreferredSize(4, 1))
        steveThrush.addComponent(label().withText("Steve").withPreferredSize(12, 1))
        steveThrush.addComponent(label().withText("Thrush").withPreferredSize(12, 1))
        steveThrush.addComponent(label().withText("Farmer").withPreferredSize(12, 1))
        table.addComponent(headerRow)
        table.addComponent(samLawrey)
        table.addComponent(janeFisher)
        table.addComponent(johnSmith)
        table.addComponent(steveThrush)
        box.addComponent(table)
    }
}