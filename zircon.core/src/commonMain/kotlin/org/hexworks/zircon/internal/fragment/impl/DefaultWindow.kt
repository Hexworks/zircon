package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.internal.graphics.linesFor
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Window

public class DefaultWindow internal constructor(
    width: Int,
    initialTitle: String,
    initialHeader: String,
    initialBody: String,
    controls: String,
    theme: ComponentTheme,
) : Window {

    private val components = ComponentFactory { theme }
    private val titleLabel: Label
    private val headerLabel: Label
    private val bodyParagraph: Paragraph
    override val root: VBox

    override var title: String
        get() = titleLabel.text
        set(value) {
            titleLabel.text = value
        }
    override var header: String
        get() = headerLabel.text
        set(value) {
            headerLabel.text = value
        }
    override var body: String
        get() = bodyParagraph.text
        set(value) {
            bodyParagraph.text = value
        }

    init {
        val boxBuilder = components.configure(VBoxBuilder(), ComponentRole.WINDOW)
        val inner = maxOf(5, width - boxBuilder.decorationsSize.width)
        val controlsWidth = controls.length
        titleLabel = components.label {
            role = ComponentRole.WINDOW
            part = Part.TITLE
            preferredSize = Size(maxOf(1, inner - controlsWidth - 1), 1)
            +initialTitle
        }
        val controlsLabel = components.label {
            role = ComponentRole.WINDOW
            part = Part.CONTROLS
            preferredSize = Size(controlsWidth, 1)
            +controls
        }
        val titleBar = components.hBox {
            role = ComponentRole.WINDOW
            part = Part.TITLE_BAR
            spacing = 1
            preferredContentSize = Size(inner, 1)
            childrenToAdd = listOf(titleLabel, controlsLabel)
        }
        headerLabel = components.label {
            role = ComponentRole.WINDOW
            part = Part.HEADER
            preferredSize = Size(inner, 1)
            +initialHeader
        }
        val bodyLines = linesFor(initialBody, inner)
        bodyParagraph = components.paragraph {
            role = ComponentRole.WINDOW
            part = Part.BODY
            preferredSize = Size(inner, bodyLines)
            +initialBody
        }
        // Rows: title bar, a blank row the theme may draw a divider on, header, blank, body.
        root = boxBuilder.apply {
            spacing = 1
            preferredContentSize = Size(inner, 1 + 1 + 1 + 1 + bodyLines)
            childrenToAdd = listOf(titleBar, headerLabel, bodyParagraph)
        }.build()
    }
}
