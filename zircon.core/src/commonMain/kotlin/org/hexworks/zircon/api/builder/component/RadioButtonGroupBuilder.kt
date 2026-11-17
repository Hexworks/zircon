@file:Suppress("DEPRECATION")

package org.hexworks.zircon.api.builder.component

import org.hexworks.zircon.api.component.BinarySelector
import org.hexworks.zircon.api.component.ExclusiveSelectionGroup

@Deprecated("Use ExclusiveSelectionGroupBuilder.", ReplaceWith("ExclusiveSelectionGroupBuilder<BinarySelector>"))
public typealias RadioButtonGroupBuilder = ExclusiveSelectionGroupBuilder<BinarySelector>

@Deprecated("Use members.", ReplaceWith("members"))
public var ExclusiveSelectionGroupBuilder<BinarySelector>.radioButtons: List<BinarySelector>
    get() = members
    set(value) {
        members = value
    }

/**
 * Creates a new group that keeps one radio button selected.
 */
@Deprecated("Use selectorGroup { }.", ReplaceWith("selectorGroup(init)"))
public fun radioButtonGroup(init: ExclusiveSelectionGroupBuilder<BinarySelector>.() -> Unit): ExclusiveSelectionGroup<BinarySelector> =
    selectorGroup(init)
