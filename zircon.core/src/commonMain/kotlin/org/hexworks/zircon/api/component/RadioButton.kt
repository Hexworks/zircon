package org.hexworks.zircon.api.component

/**
 * A radio button is a [BinarySelector] with the `RADIO_BUTTON` role that is only unselected
 * by its [ExclusiveSelectionGroup].
 */
@Deprecated("CheckBox, RadioButton and ToggleButton are one BinarySelector since 2.0.", ReplaceWith("BinarySelector"))
public typealias RadioButton = BinarySelector
