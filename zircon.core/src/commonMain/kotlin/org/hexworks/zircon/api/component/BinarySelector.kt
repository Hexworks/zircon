package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Selectable
import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [BinarySelector] is a labelled [Component] that is either selected or not: a check box, a
 * radio button and a toggle button are all one of these, told apart by their
 * [org.hexworks.zircon.api.component.theme.ComponentRole] (which picks the look) and by
 * [allowUnselect] (which picks the behavior).
 *
 * A press shows the pending state ([isPressed]) and a release on the selector commits it:
 * it toggles [isSelected] when [allowUnselect] is `true`, and only ever selects when it is
 * `false` (radio behavior). A release outside the selector cancels the press.
 *
 * Put selectors in an [ExclusiveSelectionGroup] to keep exactly one of them selected.
 */
public interface BinarySelector : Component, Selectable, TextOverride {

    /**
     * Whether a release on a selected selector unselects it. `false` is the radio button
     * behavior: the selector is only ever unselected by its [ExclusiveSelectionGroup].
     */
    public val allowUnselect: Boolean

    /**
     * `true` between the press (activation) and the release (deactivation) on this selector,
     * when the look shows the pending state (`[+]`, `[-]`, `<o>`).
     */
    public val isPressed: Boolean

    /**
     * Which side of the label the mark is drawn on.
     */
    public val labelAlignment: LabelAlignment

    /**
     * Where the mark of a [BinarySelector] sits next to its label.
     */
    public enum class LabelAlignment {
        LEFT,
        RIGHT
    }
}
