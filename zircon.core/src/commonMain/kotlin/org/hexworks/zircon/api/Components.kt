package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic

object Components {

    @JvmStatic
    fun textArea() = TextAreaBuilder.newBuilder()

    @JvmStatic
    fun horizontalNumberInput(width: Int) = HorizontalNumberInputBuilder.newBuilder(width)

    @JvmStatic
    fun verticalNumberInput(height: Int) = VerticalNumberInputBuilder.newBuilder(height)

    @JvmStatic
    fun logArea() = LogAreaBuilder.newBuilder()

    @JvmStatic
    fun textBox(contentWidth: Int) = TextBoxBuilder.newBuilder(contentWidth)

    @JvmStatic
    fun radioButtonGroup() = RadioButtonGroupBuilder.newBuilder()

    @JvmStatic
    fun panel() = PanelBuilder.newBuilder()

    @JvmStatic
    fun label() = LabelBuilder.newBuilder()

    @JvmStatic
    fun button() = ButtonBuilder.newBuilder()

    @JvmStatic
    fun toggleButton() = ToggleButtonBuilder.newBuilder()

    @JvmStatic
    fun checkBox() = CheckBoxBuilder.newBuilder()

    @JvmStatic
    fun progressBar() = ProgressBarBuilder.newBuilder()

    @JvmStatic
    fun <T : Tile, B : Block<T>> gameComponent() = GameComponentBuilder.newBuilder<T, B>()

    @JvmStatic
    fun header() = HeaderBuilder.newBuilder()

    @JvmStatic
    fun paragraph() = ParagraphBuilder.newBuilder()

    @JvmStatic
    fun listItem() = ListItemBuilder.newBuilder()

    @JvmStatic
    fun icon() = IconBuilder.newBuilder()

    @JvmStatic
    fun hbox() = HBoxBuilder.newBuilder()

    @JvmStatic
    fun vbox() = VBoxBuilder.newBuilder()

    @JvmStatic
    fun horizontalSlider() = HorizontalSliderBuilder.newBuilder()

    @JvmStatic
    fun verticalSlider() = VerticalSliderBuilder.newBuilder()

    @JvmStatic
    fun horizontalScrollbar() = HorizontalScrollBarBuilder.newBuilder()

    @JvmStatic
    fun verticalScrollbar() = VerticalScrollBarBuilder.newBuilder()
}
