package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.*

object Components {

    @JvmStatic
    fun textBox() = TextBoxBuilder.newBuilder()

    @JvmStatic
    fun radioButtonGroup() = RadioButtonGroupBuilder.newBuilder()

    @JvmStatic
    fun panel() = PanelBuilder.newBuilder()

    @JvmStatic
    fun label() = LabelBuilder.newBuilder()

    @JvmStatic
    fun button() = ButtonBuilder.newBuilder()

    @JvmStatic
    fun checkBox() = CheckBoxBuilder.newBuilder()

    @JvmStatic
    fun gameComponent() = GameComponentBuilder.newBuilder()

    @JvmStatic
    fun header() = HeaderBuilder.newBuilder()

}
