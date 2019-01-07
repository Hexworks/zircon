package org.hexworks.zircon.api.kotlin

import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.zircon.api.component.RadioButtonGroup
import org.hexworks.zircon.api.component.RadioButtonGroup.Selection

/**
 * Extension function which adapts [RadioButtonGroup.onSelection] to
 * Kotlin idioms (eg: lambdas).
 */
inline fun RadioButtonGroup.onSelection(crossinline fn: (Selection) -> Unit) {
    onSelection(object : Consumer<Selection> {
        override fun accept(value: Selection) {
            fn(value)
        }
    })
}
