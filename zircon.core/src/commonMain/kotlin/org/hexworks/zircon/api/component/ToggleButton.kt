package org.hexworks.zircon.api.component

/**
 * A toggle button is a [BinarySelector] with the `TOGGLE_BUTTON` role that can be unselected.
 */
@Deprecated("CheckBox, RadioButton and ToggleButton are one BinarySelector since 2.0.", ReplaceWith("BinarySelector"))
public typealias ToggleButton = BinarySelector
