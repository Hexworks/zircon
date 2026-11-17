package org.hexworks.zircon.api.component

/**
 * A check box is a [BinarySelector] with the `CHECK_BOX` role that can be unselected.
 */
@Deprecated("CheckBox, RadioButton and ToggleButton are one BinarySelector since 2.0.", ReplaceWith("BinarySelector"))
public typealias CheckBox = BinarySelector

@Deprecated("Use BinarySelector.LabelAlignment.", ReplaceWith("BinarySelector.LabelAlignment"))
public typealias CheckBoxAlignment = BinarySelector.LabelAlignment
