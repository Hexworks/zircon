package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.datatypes.Maybe
import kotlin.jvm.Synchronized

/**
 * A [RadioButtonGroup] is a specialization of a [Group] which will enforce
 * that only one [RadioButton] is selected in it at all times.
 */
interface RadioButtonGroup : Group<RadioButton> {

    var selectedButton: Maybe<RadioButton>
    val selectedButtonProperty: Property<Maybe<RadioButton>>

    /**
     * Adds the given [component] to this [Group]. After the addition is complete
     * the [ComponentProperties] of the given [component] will be updated whenever this
     * [Group]'s properties are updated. Has no effect if the [component] is already in this [Group].
     */
    override fun add(component: RadioButton)

    /**
     * Removes the given [component] from this [Group]. After the removal the given
     * [component] won't be updated anymore when the properties of this [Group] change.
     * Note that this function has no effect if the given [component] was not part of this group.
     */
    override fun remove(component: RadioButton)
}
