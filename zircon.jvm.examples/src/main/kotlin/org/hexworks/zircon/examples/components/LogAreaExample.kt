package org.hexworks.zircon.examples.components

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.Components.button
import org.hexworks.zircon.api.Components.logArea
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.color.TileColor.Companion.defaultBackgroundColor
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.examples.base.OneColumnComponentExample

object LogAreaExample : OneColumnComponentExample() {

    @JvmStatic
    fun main(args: Array<String>) {
        LogAreaExample.show("Log Area Example")
    }

    override fun build(box: VBox) {
        val logArea = logArea()
            .withDecorations(box(BoxType.DOUBLE, "Log"))
            .withPreferredSize(box.contentSize.withRelativeHeight(-1))
            .build()
        logArea.addParagraph("This is a simple log row", false, 0)
        logArea.addParagraph("This is a log row with a typing effect", false, 200)
        logArea.addNewRows(2)
        logArea.addInlineText("This is a log row with a ")
        val btn = button()
            .withText("Button")
            .build()
        logArea.addInlineComponent(btn)
        logArea.commitInlineElements()
        logArea.addNewRows(2)
        logArea.addParagraph("This is a long log row, which gets wrapped, since it is long", false, 0)
        logArea.addNewRows(1)
        val paragraphStyleSet = ComponentStyleSet.newBuilder()
            .withDefaultStyle(
                StyleSet.newBuilder()
                    .withForegroundColor(ANSITileColor.YELLOW)
                    .withBackgroundColor(defaultBackgroundColor())
                    .build()
            )
            .build()
        logArea.addParagraph(
            ParagraphBuilder.newBuilder()
                .withText("This is a long log row, which gets wrapped, since it is long with a different style")
                .withComponentStyleSet(paragraphStyleSet), false
        )
        box.addComponent(logArea)
    }
}