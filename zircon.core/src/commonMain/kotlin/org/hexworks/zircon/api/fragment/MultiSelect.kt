package org.hexworks.zircon.api.fragment

import org.hexworks.zircon.api.component.Fragment

/**
 * A MultiSelect is a one line input to select one of multiple values of type [T].
 * It gets a list of objects you can cycle through with a left and a right button.
 * You must provide the total width of the [Fragment] it may take up in its parent,
 * the list of values to cycle through and a callback method.
 * This callback will be invoked when the value changes (i.e. the uer pressed one of the buttons) and gets
 * the old value and the new value as parameters.
 * Optionally you can specify if the text on the label should be centered, default is true.
 * If the toString method of [T] is not well suited for the label, you can pass a different one.
 */
interface MultiSelect<T: Any>: Fragment {
    val values: List<T>
    val callback: (oldValue: T, newValue: T) -> Unit
}