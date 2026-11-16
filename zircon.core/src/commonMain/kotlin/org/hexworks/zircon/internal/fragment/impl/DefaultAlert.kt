package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.internal.graphics.linesFor
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.Variant
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.Alert
import org.hexworks.zircon.api.fragment.AlertKind
import org.hexworks.zircon.api.graphics.StyleSet

public class DefaultAlert internal constructor(
    override val kind: AlertKind,
    initialText: String,
    width: Int,
    icon: Char,
    theme: ComponentTheme?,
) : Alert {

    private val components = ComponentFactory { theme }
    private val paragraph: Paragraph
    override val root: Panel

    override var text: String
        get() = paragraph.text
        set(value) {
            paragraph.text = value
        }

    init {
        val variant = VARIANTS.getValue(kind)
        val panelBuilder = components.configure(PanelBuilder(), ComponentRole.ALERT, variant)
        val inner = maxOf(3, width - panelBuilder.decorationsSize.width)
        val textWidth = inner - 2
        val lines = linesFor(initialText, textWidth)
        val iconComponent = components.icon {
            role = ComponentRole.ALERT
            this.variant = variant
            part = Part.ICON
            iconTile = characterTile {
                character = icon
                styleSet = StyleSet.defaultStyle()
            }
        }
        paragraph = components.paragraph {
            role = ComponentRole.ALERT
            this.variant = variant
            part = Part.TEXT
            preferredSize = Size(textWidth, lines)
            position = Position(2, 0)
            +initialText
        }
        root = panelBuilder.apply {
            preferredContentSize = Size(inner, lines)
            childrenToAdd = listOf(iconComponent, paragraph)
        }.build()
    }

    private companion object {
        val VARIANTS = mapOf(
            AlertKind.INFO to Variant.INFO,
            AlertKind.SUCCESS to Variant.SUCCESS,
            AlertKind.ERROR to Variant.ERROR,
        )
    }
}
