package org.hexworks.zircon.api.component

/**
 * A group that keeps exactly one radio button selected.
 */
@Deprecated("RadioButtonGroup is an ExclusiveSelectionGroup since 2.0.", ReplaceWith("ExclusiveSelectionGroup<BinarySelector>"))
public typealias RadioButtonGroup = ExclusiveSelectionGroup<BinarySelector>

@Deprecated("Use selected.", ReplaceWith("selected"))
public var <T> ExclusiveSelectionGroup<T>.selectedButton: T? where T : Component, T : org.hexworks.zircon.api.behavior.Selectable
    get() = selected
    set(value) {
        selected = value
    }
