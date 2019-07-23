package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.ButtonBuilder
import org.hexworks.zircon.api.builder.component.CheckBoxBuilder
import org.hexworks.zircon.api.builder.component.GameComponentBuilder
import org.hexworks.zircon.api.builder.component.HBoxBuilder
import org.hexworks.zircon.api.builder.component.HeaderBuilder
import org.hexworks.zircon.api.builder.component.IconBuilder
import org.hexworks.zircon.api.builder.component.LabelBuilder
import org.hexworks.zircon.api.builder.component.ListItemBuilder
import org.hexworks.zircon.api.builder.component.LogAreaBuilder
import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.builder.component.ParagraphBuilder
import org.hexworks.zircon.api.builder.component.ProgressBarBuilder
import org.hexworks.zircon.api.builder.component.RadioButtonGroupBuilder
import org.hexworks.zircon.api.builder.component.TextAreaBuilder
import org.hexworks.zircon.api.builder.component.TextBoxBuilder
import org.hexworks.zircon.api.builder.component.ToggleButtonBuilder
import org.hexworks.zircon.api.builder.component.VBoxBuilder
import org.hexworks.zircon.api.builder.component.SliderBuilder
import org.hexworks.zircon.api.builder.component.VerticalSliderBuilder
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic

object Components {

    @JvmStatic
    fun textArea() = TextAreaBuilder.newBuilder()

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
    fun slider() = SliderBuilder.newBuilder()

    @JvmStatic
    fun verticalSlider() = VerticalSliderBuilder.newBuilder()
}
