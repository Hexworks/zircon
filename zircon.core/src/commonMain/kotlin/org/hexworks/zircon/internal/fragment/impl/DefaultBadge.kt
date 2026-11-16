package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.fragment.Badge
import org.hexworks.zircon.api.fragment.BadgeStatus
import org.hexworks.zircon.api.graphics.StyleSet
import org.hexworks.zircon.api.uievent.ComponentEventType

public class DefaultBadge internal constructor(
    initialText: String,
    override val status: BadgeStatus,
    dismissible: Boolean,
    statusIcon: Char,
    dismissIcon: Char,
    theme: ComponentTheme?,
) : Badge {

    private val components = ComponentFactory { theme }
    private val label: Label
    private val dismissHandlers = mutableListOf<() -> Unit>()
    override val root: HBox

    override var text: String
        get() = label.text
        set(value) {
            label.text = value
        }

    override var isDismissed: Boolean = false
        private set

    init {
        val parts = mutableListOf<Component>()
        if (status != BadgeStatus.NONE) {
            parts += components.icon {
                role = if (status == BadgeStatus.SUCCESS) ComponentRole.BADGE_DOT_SUCCESS else ComponentRole.BADGE_DOT_ERROR
                iconTile = characterTile {
                    character = statusIcon
                    styleSet = StyleSet.defaultStyle()
                }
            }
        }
        label = components.label {
            role = ComponentRole.BADGE_TEXT
            +initialText
        }
        parts += label
        if (dismissible) {
            parts += components.button {
                role = ComponentRole.BADGE_DISMISS
                +dismissIcon.toString()
                processComponentEvents(ComponentEventType.ACTIVATED) { dismiss() }
            }
        }
        root = components.hBox {
            role = ComponentRole.BADGE
            spacing = 1
            childrenToAdd = parts
        }
    }

    override fun onDismissed(fn: () -> Unit) {
        dismissHandlers += fn
    }

    private fun dismiss() {
        if (isDismissed) return
        isDismissed = true
        root.isHidden = true
        dismissHandlers.forEach { it() }
    }
}
