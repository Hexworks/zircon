package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.header
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.paragraph
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.Size

/** The tabs of the tab bar examples: twelve, so the bars have to scroll. */
internal val TAB_NAMES: List<String> = listOf(
    "Mercury", "Venus", "Earth", "Mars", "Jupiter", "Saturn", "Uranus", "Neptune", "Pluto", "Ceres", "Eris", "Haumea",
)

/** The content of one tab: a heading, a paragraph and a button that counts its presses. */
internal fun tabContent(name: String, size: Size): Component {
    val root = vBox {
        preferredSize = size
        spacing = 1
        decorations { +box(title = name) }
    }
    root.addComponent(header { +"Tab: $name" })
    root.addComponent(paragraph {
        +"This is the content of the $name tab. The strip above or beside it scrolls when the tabs do not fit: use the arrow tabs or the mouse wheel."
        preferredSize = Size(root.contentSize.width, 4)
    })
    val counter = label { +"Pressed 0 times" }
    var presses = 0
    root.addComponent(button {
        +"Press me"
        onActivated {
            presses++
            counter.text = "Pressed $presses times"
        }
    })
    root.addComponent(counter)
    return root
}
