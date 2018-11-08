package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Subscription
import org.hexworks.zircon.api.util.Runnable
import org.hexworks.zircon.internal.component.impl.DefaultRadioButton.RadioButtonState

interface RadioButton : Component {

    val text: String
    val state: RadioButtonState

    fun isSelected(): Boolean

    fun onSelected(runnable: Runnable) : Subscription

    fun select()

}
