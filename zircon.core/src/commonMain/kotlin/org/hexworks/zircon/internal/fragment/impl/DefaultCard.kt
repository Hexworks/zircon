package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.internal.graphics.linesFor
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Card
import org.hexworks.zircon.api.graphics.StyleSet

public class DefaultCard internal constructor(
    width: Int,
    icon: Char?,
    initialSubtitle: String,
    initialTitle: String,
    initialBody: String,
    theme: ComponentTheme?,
) : Card {

    private val components = ComponentFactory { theme }
    private val subtitleLabel: Label
    private val titleParagraph: Paragraph
    private val bodyParagraph: Paragraph
    override val root: VBox

    override var subtitle: String
        get() = subtitleLabel.text
        set(value) {
            subtitleLabel.text = value
        }
    override var title: String
        get() = titleParagraph.text
        set(value) {
            titleParagraph.text = value
        }
    override var body: String
        get() = bodyParagraph.text
        set(value) {
            bodyParagraph.text = value
        }

    init {
        val boxBuilder = components.configure(VBoxBuilder(), ComponentRole.CARD)
        val inner = maxOf(3, width - boxBuilder.decorationsSize.width)
        val metaParts = mutableListOf<Component>()
        var subtitleWidth = inner
        if (icon != null) {
            metaParts += components.icon {
                role = ComponentRole.CARD_ICON
                iconTile = characterTile {
                    character = icon
                    styleSet = StyleSet.defaultStyle()
                }
            }
            subtitleWidth -= 2
        }
        subtitleLabel = components.label {
            role = ComponentRole.CARD_SUBTITLE
            preferredSize = Size(subtitleWidth, 1)
            +initialSubtitle
        }
        metaParts += subtitleLabel
        val meta = components.hBox {
            role = ComponentRole.CARD_META
            spacing = 1
            preferredContentSize = Size(inner, 1)
            childrenToAdd = metaParts
        }
        val titleLines = linesFor(initialTitle, inner)
        titleParagraph = components.paragraph {
            role = ComponentRole.CARD_TITLE
            preferredSize = Size(inner, titleLines)
            +initialTitle
        }
        val bodyLines = linesFor(initialBody, inner)
        bodyParagraph = components.paragraph {
            role = ComponentRole.CARD_BODY
            preferredSize = Size(inner, bodyLines)
            +initialBody
        }
        root = boxBuilder.apply {
            spacing = 1
            preferredContentSize = Size(inner, 1 + 1 + titleLines + 1 + bodyLines)
            childrenToAdd = listOf(meta, titleParagraph, bodyParagraph)
        }.build()
    }
}
