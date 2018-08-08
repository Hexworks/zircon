package org.codetome.zircon.jvm.api.interop

import org.codetome.zircon.api.builder.component.*

object Components {

    @JvmStatic
    fun newTextBoxBuilder() = TextBoxBuilder.newBuilder()

    @JvmStatic
    fun newRadioButtonGroupBuilder() = RadioButtonGroupBuilder.newBuilder()

    @JvmStatic
    fun newPanelBuilder() = PanelBuilder.newBuilder()

    @JvmStatic
    fun newLabelBuilder() = LabelBuilder.newBuilder()

    @JvmStatic
    fun newButtonBuilder() = ButtonBuilder.newBuilder()

    @JvmStatic
    fun newCheckBoxBuilder() = CheckBoxBuilder.newBuilder()

    @JvmStatic
    fun newGameComponentBuilder() = GameComponentBuilder.newBuilder()

    @JvmStatic
    fun newHeaderBuilder() = HeaderBuilder.newBuilder()

}
