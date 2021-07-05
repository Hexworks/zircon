package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe

/**
 * A [RadioButtonGroup] is a specialization of a [Group] that will enforce
 * that only one [RadioButton] is selected in it at all times.
 */
interface RadioButtonGroup : Group<RadioButton> {

    // TODO: how to use nullables instead?
    var selectedButton: Maybe<RadioButton>
    val selectedButtonProperty: Property<Maybe<RadioButton>>

}
