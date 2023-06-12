package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.component.*

/**
 * This object contains factory methods for creating [Component] builders.
 */
object Components {

    /**
     * Creates a new [TextAreaBuilder] for building [TextArea]s.
     * @see TextArea
     */
    fun textArea() = TextAreaBuilder.newBuilder()

    /**
     * Creates a new [HorizontalNumberInputBuilder] for building horizontal [NumberInput] components.
     * @see NumberInput
     */
    fun horizontalNumberInput() = HorizontalNumberInputBuilder.newBuilder()

    /**
     * Creates a new [LogAreaBuilder] for building [LogArea] components.
     * @see LogArea
     */
    fun logArea() = LogAreaBuilder.newBuilder()

    /**
     * Creates a new [TextBoxBuilder] for building [TextBox] components.
     * @see TextBox
     */
    fun textBox(contentWidth: Int) = TextBoxBuilder.newBuilder(contentWidth)

    /**
     * Creates a new [PanelBuilder] for building [Panel] components.
     * @see Panel
     */
    fun panel() = PanelBuilder.newBuilder()

    /**
     * Creates a new [LabelBuilder] for building [Label] components.
     * @see Label
     */
    fun label() = LabelBuilder.newBuilder()

    /**
     * Creates a new [ButtonBuilder] for building [Button] components.
     * @see Button
     */
    fun button() = ButtonBuilder.newBuilder()

    /**
     * Creates a new [ToggleButtonBuilder] for building [ToggleButton] components.
     * @see ToggleButton
     */
    fun toggleButton() = ToggleButtonBuilder.newBuilder()

    /**
     * Creates a new [CheckBoxBuilder] for building [CheckBox] components.
     * @see CheckBox
     */
    fun checkBox() = CheckBoxBuilder.newBuilder()

    /**
     * Creates a new [ProgressBarBuilder] for building [ProgressBar] components.
     * @see ProgressBar
     */
    fun progressBar() = ProgressBarBuilder.newBuilder()

    /**
     * Creates a new [HeaderBuilder] for building [Header] components.
     * @see Header
     */
    fun header() = HeaderBuilder.newBuilder()

    /**
     * Creates a new [ParagraphBuilder] for building [Paragraph] components.
     * @see Paragraph
     */
    fun paragraph() = ParagraphBuilder.newBuilder()

    /**
     * Creates a new [ListItemBuilder] for building [ListItem] components.
     * @see ListItem
     */
    fun listItem() = ListItemBuilder.newBuilder()

    /**
     * Creates a new [IconBuilder] for building [Icon] components.
     * @see Icon
     */
    fun icon() = IconBuilder.newBuilder()

    /**
     * Creates a new [HBoxBuilder] for building [HBox] components.
     * @see HBox
     */
    fun hbox() = HBoxBuilder.newBuilder()

    /**
     * Creates a new [VBoxBuilder] for building [VBox] components.
     * @see VBox
     */
    fun vbox() = VBoxBuilder.newBuilder()

    /**
     * Creates a new [HorizontalSliderBuilder] for building [Slider] components.
     * @see Slider
     */
    fun horizontalSlider() = HorizontalSliderBuilder.newBuilder()

    /**
     * Creates a new [VerticalSliderBuilder] for building [Slider] components.
     * @see Slider
     */
    fun verticalSlider() = VerticalSliderBuilder.newBuilder()

    /**
     * Creates a new [HorizontalScrollBarBuilder] for building horizontal [ScrollBar] components.
     * @see ScrollBar
     */
    fun horizontalScrollbar() = HorizontalScrollBarBuilder.newBuilder()

    /**
     * Creates a new [VerticalScrollBarBuilder] for building vertical [ScrollBar] components.
     * @see ScrollBar
     */
    fun verticalScrollbar() = VerticalScrollBarBuilder.newBuilder()

    /**
     * Creates a new [RadioButtonBuilder] for building [RadioButton] components.
     * @see RadioButton
     */
    fun radioButton() = RadioButtonBuilder.newBuilder()

    /**
     * Creates a new [RadioButtonGroupBuilder] for building [RadioButtonGroup]s.
     * Note that a [RadioButtonGroup] is not a [Component], but a logical grouping.
     * @see RadioButtonGroup
     */
    fun radioButtonGroup() = RadioButtonGroupBuilder.newBuilder()

    /**
     * Creates a new [GroupBuilder] for building [Group]s.
     * Note that a [Group] is not a [Component], but a logical grouping.
     * @see Group
     */
    fun <T : Component> group() = GroupBuilder.newBuilder<T>()

}
