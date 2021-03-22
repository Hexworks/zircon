package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.CheckBoxBuilder
import org.hexworks.zircon.api.component.CheckBox
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox

fun checkBox(
    labelAlignment: DefaultCheckBox.CheckBoxAlignment = DefaultCheckBox.CheckBoxAlignment.RIGHT,
    init: CheckBoxBuilder.() -> Unit
): CheckBox =
    CheckBoxBuilder(labelAlignment = labelAlignment).apply(init).build()