package org.hexworks.zircon.examples.other

import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.GraphicalTilesetResources
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.icon
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.listItem
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.data.graphicalTile
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/**
 * A character sheet: headers, icons from a graphical tileset next to labels, and a text
 * block of paragraphs and list items, in a boxed and shadowed panel.
 */
public val statsPanelExample: Example = Example("Stats panel", textOnly = false) { context ->
    val root = panel { preferredSize = context.contentSize }
    val nethack = GraphicalTilesetResources.nethack16x16()
    val sheet = vBox {
        decorations {
            +box(BoxType.SINGLE, "Sel Darkstrom")
            +shadow()
        }
        preferredSize = Size(20, 22)
        alignment = alignmentWithin(root, ComponentAlignment.CENTER)
    }
    val width = sheet.contentSize.width
    sheet.addComponent(label { preferredSize = Size(1, 1) }) // spacer
    sheet.addComponent(header { +"Stats" })
    sheet.addComponent(hBox {
        preferredSize = Size(width, 1)
        icon { iconTile = graphicalTile { name = "Short sword"; tileset = nethack } }
        label { +"Attack:5" }
    })
    sheet.addComponent(hBox {
        preferredSize = Size(width, 1)
        icon { iconTile = graphicalTile { name = "Small shield"; tileset = nethack } }
        label { +"Defense:4" }
    })
    sheet.addComponent(label { preferredSize = Size(1, 1) })
    sheet.addComponent(header { +"Info" })
    sheet.addComponent(vBox {
        preferredSize = Size(width, 10)
        paragraph {
            +"A short, sturdy creature fond of drink and industry."
            preferredSize = Size(width, 4)
        }
        paragraph {
            +"Abilities:"
            preferredSize = Size(width, 1)
        }
        listItem { +"Drink" }
        listItem { +"Dig" }
        listItem { +"Sleep" }
    })
    root.addComponent(sheet)
    ExampleView(root)
}
