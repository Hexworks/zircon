package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.internal.graphics.linesFor
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.Panel
import org.hexworks.zircon.api.component.Paragraph
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
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
        val panelBuilder = components.configure(PanelBuilder(), PANEL_ROLES.getValue(kind))
        val inner = maxOf(3, width - panelBuilder.decorationsSize.width)
        val textWidth = inner - 2
        val lines = linesFor(initialText, textWidth)
        val textRole = TEXT_ROLES.getValue(kind)
        val iconComponent = components.icon {
            role = textRole
            iconTile = characterTile {
                character = icon
                styleSet = StyleSet.defaultStyle()
            }
        }
        paragraph = components.paragraph {
            role = textRole
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
        val PANEL_ROLES = mapOf(
            AlertKind.INFO to ComponentRole.ALERT_INFO,
            AlertKind.SUCCESS to ComponentRole.ALERT_SUCCESS,
            AlertKind.ERROR to ComponentRole.ALERT_ERROR,
        )
        val TEXT_ROLES = mapOf(
            AlertKind.INFO to ComponentRole.ALERT_INFO_TEXT,
            AlertKind.SUCCESS to ComponentRole.ALERT_SUCCESS_TEXT,
            AlertKind.ERROR to ComponentRole.ALERT_ERROR_TEXT,
        )
    }
}
