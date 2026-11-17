package org.hexworks.zircon.internal.component.impl

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride
import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.BinarySelector.LabelAlignment
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.renderer.ComponentRenderingStrategy
import org.hexworks.zircon.api.extensions.whenEnabledRespondWith
import org.hexworks.zircon.api.uievent.MouseEvent
import org.hexworks.zircon.api.uievent.UIEventPhase
import org.hexworks.zircon.api.uievent.UIEventResponse

/**
 * The one implementation behind check boxes, radio buttons and toggle buttons. A press shows
 * the pending state for as long as it lasts, even when the cursor leaves (the dispatcher keeps
 * delivering the press); only the colours follow the hover. The release commits, unless it
 * happens outside, which cancels like a button in a browser.
 */
public class DefaultBinarySelector internal constructor(
    componentMetadata: ComponentMetadata,
    textProperty: Property<String>,
    key: String,
    initialSelected: Boolean,
    override val allowUnselect: Boolean,
    override val labelAlignment: LabelAlignment,
    renderingStrategy: ComponentRenderingStrategy<BinarySelector>,
) : BinarySelector, DefaultComponent(
    metadata = componentMetadata,
    renderer = renderingStrategy
),
    TextOverride by TextOverride.create(textProperty),
    Selectable by Selectable.create(initialSelected, key) {

    override var isPressed: Boolean = false
        private set

    /** `true` while [isPressed] and the cursor is outside the selector: a release there cancels. */
    private var cursorOutside = false

    override fun mouseExited(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET && isPressed) cursorOutside = true
        super.mouseExited(event, phase)
    }

    override fun mouseEntered(event: MouseEvent, phase: UIEventPhase): UIEventResponse = whenEnabledRespondWith {
        if (phase == UIEventPhase.TARGET && isPressed) cursorOutside = false
        super.mouseEntered(event, phase)
    }

    override fun activated(): UIEventResponse = whenEnabledRespondWith {
        isPressed = true
        cursorOutside = false
        super.activated()
    }

    override fun deactivated(): UIEventResponse = whenEnabledRespondWith {
        if (isPressed && !cursorOutside) {
            isSelected = if (allowUnselect) isSelected.not() else true
        }
        isPressed = false
        super.deactivated()
    }

    override fun focusTaken(): UIEventResponse = whenEnabledRespondWith {
        isPressed = false
        super.focusTaken()
    }
}
