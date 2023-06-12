package org.hexworks.zircon.api.fragment

import org.hexworks.cobalt.databinding.api.collection.ListProperty
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.component.Fragment

/**
 * A [Selector] is a one line input to select a value of type [T] from a list of values.
 * It gets a list of objects you can cycle through with a left and a right button.
 *
 * You must provide the total width of the [Fragment] it may take up within its parent,
 * and a list of values to cycle through.
 *
 * Optionally you can specify if the text on the label should be centered, default is `true`.
 * If the `toString` method of [T] is not well suited for the label, you can pass a different one.
 */
interface Selector<T : Any> : Fragment {

    val values: List<T>
    val valuesProperty: ListProperty<T>

    val selected: T
    val selectedValue: ObservableValue<T>
}
