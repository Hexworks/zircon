package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.data.Tile

object Components {

    @JvmStatic
    fun textArea() = TextAreaBuilder.newBuilder()

    @JvmStatic
    fun logArea() = LogAreaBuilder.newBuilder()

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
    fun <T : Tile> gameComponent() = GameComponentBuilder.newBuilder<T>()

    @JvmStatic
    fun header() = HeaderBuilder.newBuilder()

}
