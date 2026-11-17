package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.behavior.Selectable

/**
 * An [ExclusiveSelectionGroup] is a [Group] of [Selectable] components that keeps at most one
 * of them selected: selecting a member unselects the previous selection. Members are told
 * apart by their [Selectable.key], which must be unique within the group.
 */
public interface ExclusiveSelectionGroup<T> : Group<T> where T : Component, T : Selectable {

    public val hasSelection: Boolean
        get() = selected != null

    /**
     * The selected member, `null` when there is no selection. Setting it selects that member.
     */
    public var selected: T?

    /**
     * The selected member wrapped in a [Property], `null` when there is no selection.
     */
    public val selectedProperty: Property<T?>
}
