package org.hexworks.zircon.examples

import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.application.appConfig
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.checkBox
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.progressBar
import org.hexworks.zircon.api.builder.component.radioButton
import org.hexworks.zircon.api.builder.component.textArea
import org.hexworks.zircon.api.builder.component.toggleButton
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.createTileGrid
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.uievent.ComponentEventType

/**
 * The usual suspects: every interactive component in one screen.
 */
public fun componentsDemo(): TileGrid {
    val grid = createTileGrid(
        appConfig {
            size = Size(60, 30)
            defaultTileset = CP437TilesetResources.wanderlust16x16()
        }
    )
    val screen = grid.toScreen()
    screen.theme = ColorThemes.arc()

    val root = hBox {
        preferredSize = Size(60, 30)
        decorations { +box(title = "Zircon 2.0 components") }
    }
    screen.addComponent(root)

    val left = vBox { preferredSize = Size(28, 28) }
    val right = vBox { preferredSize = Size(28, 28) }
    root.addComponent(left)
    root.addComponent(right)

    left.addComponent(header { +"Buttons" })
    val clicks = label { +"Clicked 0 times" }
    var count = 0
    left.addComponent(button { +"Click me" }.apply {
        handleComponentEvents(ComponentEventType.ACTIVATED) {
            count++
            clicks.text = "Clicked $count times"
            org.hexworks.zircon.api.uievent.Processed
        }
    })
    left.addComponent(clicks)
    left.addComponent(toggleButton { +"Toggle" })
    left.addComponent(checkBox { +"Check box" })
    left.addComponent(radioButton { +"Radio"; key = "a" })
    left.addComponent(header { +"Progress" })
    left.addComponent(progressBar { preferredSize = Size(20, 1); range = 100; numberOfSteps = 20 }.apply { progress = 60.0 })

    right.addComponent(header { +"Text" })
    right.addComponent(paragraph {
        +"Paragraphs wrap their text to the width of the component, so long sentences look fine."
        preferredSize = Size(28, 4)
    })
    right.addComponent(textArea {
        text = "Editable text area"
        preferredSize = Size(28, 6)
        decorations { +box() }
    })

    screen.display()
    return grid
}
