package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property


/**
 * A [RadioButtonGroup] is a specialization of a [Group] that will enforce
 * that only one [RadioButton] is selected in it at all times.
 */
interface RadioButtonGroup : Group<RadioButton> {


    val hasSelection: Boolean
        get() = selectedButton != null

    /**
     * The selected button. Will have `null` value if there is no selection.
     */
    var selectedButton: RadioButton?

    /**
     * The selected button wrapped in a [Property]. Will have the value
     * of `null` if there is no selection
     */
    val selectedButtonProperty: Property<RadioButton?>

}
