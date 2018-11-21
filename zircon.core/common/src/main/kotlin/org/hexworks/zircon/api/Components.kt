package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic

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
    fun <T: Tile, B : Block<T>> gameComponent() = GameComponentBuilder.newBuilder<T, B>()

    @JvmStatic
    fun header() = HeaderBuilder.newBuilder()

}
