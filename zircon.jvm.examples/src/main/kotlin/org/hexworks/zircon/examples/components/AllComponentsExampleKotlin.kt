package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.Components.horizontalNumberInput
import org.hexworks.zircon.api.Components.horizontalSlider
import org.hexworks.zircon.api.Components.icon
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.listItem
import org.hexworks.zircon.api.Components.paragraph
import org.hexworks.zircon.api.Components.progressBar
import org.hexworks.zircon.api.Components.radioButton
import org.hexworks.zircon.api.Components.radioButtonGroup
import org.hexworks.zircon.api.Components.textBox
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.GraphicalTilesetResources.nethack16x16
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Tile.Companion.newBuilder
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.base.DEFAULT_TILESET
import org.hexworks.zircon.examples.base.OneColumnComponentExampleKotlin
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import kotlin.concurrent.thread

class AllComponentsExampleKotlin : OneColumnComponentExampleKotlin() {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            AllComponentsExampleKotlin().show("All Components Example")
        }
    }

    override fun build(box: VBox) {
        val columns = hbox()
                .withComponentRenderer(NoOpComponentRenderer())
                .withSize(box.contentSize.withRelativeHeight(-1))
                .build()
        box.addComponent(columns)
        val half = columns.contentSize.width / 2
        val leftColumn = vbox()
                .withSize(columns.contentSize.withWidth(half))
                .withSpacing(1)
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Content"))
                .build()
        val rightColumn = vbox()
                .withSize(columns.contentSize.withWidth(half))
                .withSpacing(1)
                .withDecorations(box(BoxType.TOP_BOTTOM_DOUBLE, "Interactions"))
                .build()
        val columnWidth = rightColumn.contentSize.width
        leftColumn.addComponent(header().withText("This is a header"))
        leftColumn.addComponent(label().withText("This is a label"))
        leftColumn.addComponent(listItem().withText("A list item to read"))
        leftColumn.addComponent(paragraph()
                .withSize(leftColumn.contentSize.width, 3)
                .withText("And a multi-line paragraph which is very long."))
        if (DEFAULT_TILESET.size.width == 16) {
            leftColumn.addComponent(icon()
                    .withIcon(newBuilder()
                            .withTileset(nethack16x16())
                            .withName("Plate mail")
                            .buildGraphicalTile())
                    .withTileset(nethack16x16()))
        }
        leftColumn.addComponent(textBox(leftColumn.contentSize.width - 3)
                .addHeader("Text Box!")
                .withDecorations(box(), shadow())
                .addParagraph("This is a paragraph which won't fit on one line."))
        val radioBox = vbox()
                .withSize(columnWidth, 6)
                .withDecorations(box(), shadow())
                .build()
        val a = radioButton()
                .withText("Option A")
                .withKey("a")
                .build()
        val b = radioButton()
                .withText("Option B")
                .withKey("b")
                .build()
        val c = radioButton()
                .withText("Option C")
                .withKey("c")
                .build()
        radioBox.addComponent(a)
        radioBox.addComponent(b)
        radioBox.addComponent(c)
        val group = radioButtonGroup().build()
        group.addComponents(a, b, c)
        rightColumn.addComponent(radioBox)
        rightColumn.addComponent(horizontalNumberInput(columnWidth)
                .withInitialValue(5)
                .withMinValue(1)
                .withMaxValue(100)
                .withSize(columnWidth, 3)
                .withDecorations(box()))
        val progressBar = progressBar()
                .withNumberOfSteps(100)
                .withRange(100)
                .withDisplayPercentValueOfProgress(true)
                .withSize(columnWidth, 3)
                .withDecorations(box())
                .build()
        progressBar.progress = 1.0
        thread {
            try {
                while (progressBar.progress < 100) {
                    Thread.sleep(1500)
                    progressBar.progress = progressBar.progress + 1
                }
            } catch (e: InterruptedException) {
                e.printStackTrace()
            }
        }
        rightColumn.addComponent(progressBar)
        rightColumn.addComponent(horizontalSlider()
                .withMinValue(1)
                .withMaxValue(100)
                .withNumberOfSteps(100)
                .withSize(columnWidth, 3))
        columns.addComponents(leftColumn, rightColumn)
    }
}
