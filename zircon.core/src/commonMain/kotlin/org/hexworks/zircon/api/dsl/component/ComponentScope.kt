package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.internal.component.impl.DefaultCheckBox
import org.hexworks.zircon.internal.dsl.ZirconDsl

@ZirconDsl
class ComponentScope {
    private lateinit var component: Component

    fun button(init: ButtonBuilder.() -> Unit) {
        component = ButtonBuilder().apply(init).build()
    }

    fun checkBox(
        labelAlignment: DefaultCheckBox.CheckBoxAlignment = DefaultCheckBox.CheckBoxAlignment.RIGHT,
        init: CheckBoxBuilder.() -> Unit
    ) {
        component = CheckBoxBuilder(labelAlignment = labelAlignment).apply(init).build()
    }

    fun hBox(init: HBoxBuilder.() -> Unit) {
        component = HBoxBuilder().apply(init).build()
    }

    fun header(init: HeaderBuilder.() -> Unit) {
        component = HeaderBuilder().apply(init).build()
    }

    fun horizontalNumberInput(
        width: Int,
        init: HorizontalNumberInputBuilder.() -> Unit
    ) {
        component = HorizontalNumberInputBuilder(width).apply(init).build()
    }

    fun horizontalScrollBar(
        minValue: Int = 0,
        maxValue: Int = 100,
        init: HorizontalScrollBarBuilder.() -> Unit
    ) {
        component = HorizontalScrollBarBuilder(minValue, maxValue).apply(init).build()
    }

    fun horizontalSlider(init: HorizontalSliderBuilder.() -> Unit) {
        component = HorizontalSliderBuilder().apply(init).build()
    }

    fun icon(init: IconBuilder.() -> Unit) {
        component = IconBuilder().apply(init).build()
    }

    fun label(init: LabelBuilder.() -> Unit) {
        component = LabelBuilder().apply(init).build()
    }

    fun listItem(init: ListItemBuilder.() -> Unit) {
        component = ListItemBuilder().apply(init).build()
    }

    fun logArea(init: LogAreaBuilder.() -> Unit) {
        component = LogAreaBuilder().apply(init).build()
    }

    fun <T : ModalResult> modal(init: ModalBuilder<T>.() -> Unit) {
        component = ModalBuilder<T>().apply(init).build()
    }

    fun panel() {
        PanelBuilder().build()
    }

    fun paragraph(init: ParagraphBuilder.() -> Unit) {
        component = ParagraphBuilder().apply(init).build()
    }

    fun progressBar(init: ProgressBarBuilder.() -> Unit) {
        component = ProgressBarBuilder().apply(init).build()
    }

    fun radioButton(init: RadioButtonBuilder.() -> Unit) {
        component = RadioButtonBuilder().apply(init).build()
    }

    fun textArea(init: TextAreaBuilder.() -> Unit) {
        TextAreaBuilder().apply(init).build()
    }

    fun textBox(
        initialContentWidth: Int,
        nextPosition: Position = Position.defaultPosition(),
        components: MutableList<Component> = mutableListOf(),
        init: TextBoxBuilder.() -> Unit
    ) {
        component = TextBoxBuilder(initialContentWidth, nextPosition, components).apply(init).build()
    }

    fun toggleButton(init: ToggleButtonBuilder.() -> Unit) {
        ToggleButtonBuilder().apply(init).build()
    }

    fun vBox(init: VBoxBuilder.() -> Unit) {
        component = VBoxBuilder().apply(init).build()
    }

    fun verticalNumberInput(height: Int, init: VerticalNumberInputBuilder.() -> Unit) {
        component = VerticalNumberInputBuilder(height).apply(init).build()
    }

    fun verticalScrollBar(
        minValue: Int = 0,
        maxValue: Int = 100,
        init: VerticalScrollBarBuilder.() -> Unit
    ) {
        component = VerticalScrollBarBuilder(minValue, maxValue).apply(init).build()
    }

    fun verticalSlider(init: VerticalSliderBuilder.() -> Unit) {
        component = VerticalSliderBuilder().apply(init).build()
    }

    fun build(): Component = component
}
