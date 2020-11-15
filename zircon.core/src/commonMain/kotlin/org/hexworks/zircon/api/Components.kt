@file:Suppress("RUNTIME_ANNOTATION_NOT_SUPPORTED")

package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.component.*
import org.hexworks.zircon.api.component.*
import org.hexworks.zircon.api.game.GameComponent
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.TextArea
import org.hexworks.zircon.api.data.Block
import org.hexworks.zircon.api.data.Tile
import kotlin.jvm.JvmStatic

/**
 * This object contains factory methods for creating [Component] builders.
 */
object Components {

    /**
     * Creates a new [TextAreaBuilder] for building [TextArea]s.
     * @see TextArea
     */
    @JvmStatic
    fun textArea() = TextAreaBuilder.newBuilder()

    /**
     * Creates a new [HorizontalNumberInputBuilder] for building horizontal [NumberInput] components.
     * @see NumberInput
     */
    @JvmStatic
    fun horizontalNumberInput(width: Int) = HorizontalNumberInputBuilder.newBuilder(width)


    /**
     * Creates a new [VerticalNumberInputBuilder] for building vertical [NumberInput] components.
     * @see NumberInput
     */
    @JvmStatic
    fun verticalNumberInput(height: Int) = VerticalNumberInputBuilder.newBuilder(height)

    /**
     * Creates a new [LogAreaBuilder] for building [LogArea] components.
     * @see LogArea
     */
    @JvmStatic
    fun logArea() = LogAreaBuilder.newBuilder()

    /**
     * Creates a new [TextBoxBuilder] for building [TextBox] components.
     * @see TextBox
     */
    @JvmStatic
    fun textBox(contentWidth: Int) = TextBoxBuilder.newBuilder(contentWidth)

    /**
     * Creates a new [PanelBuilder] for building [Panel] components.
     * @see Panel
     */
    @JvmStatic
    fun panel() = PanelBuilder.newBuilder()

    /**
     * Creates a new [LabelBuilder] for building [Label] components.
     * @see Label
     */
    @JvmStatic
    fun label() = LabelBuilder.newBuilder()

    /**
     * Creates a new [ButtonBuilder] for building [Button] components.
     * @see Button
     */
    @JvmStatic
    fun button() = ButtonBuilder.newBuilder()

    /**
     * Creates a new [ToggleButtonBuilder] for building [ToggleButton] components.
     * @see ToggleButton
     */
    @JvmStatic
    fun toggleButton() = ToggleButtonBuilder.newBuilder()

    /**
     * Creates a new [CheckBoxBuilder] for building [CheckBox] components.
     * @see CheckBox
     */
    @JvmStatic
    fun checkBox() = CheckBoxBuilder.newBuilder()

    /**
     * Creates a new [ProgressBarBuilder] for building [ProgressBar] components.
     * @see ProgressBar
     */
    @JvmStatic
    fun progressBar() = ProgressBarBuilder.newBuilder()

    /**
     * Creates a new [HeaderBuilder] for building [Header] components.
     * @see Header
     */
    @JvmStatic
    fun header() = HeaderBuilder.newBuilder()

    /**
     * Creates a new [ParagraphBuilder] for building [Paragraph] components.
     * @see Paragraph
     */
    @JvmStatic
    fun paragraph() = ParagraphBuilder.newBuilder()

    /**
     * Creates a new [ListItemBuilder] for building [ListItem] components.
     * @see ListItem
     */
    @JvmStatic
    fun listItem() = ListItemBuilder.newBuilder()

    /**
     * Creates a new [IconBuilder] for building [Icon] components.
     * @see Icon
     */
    @JvmStatic
    fun icon() = IconBuilder.newBuilder()

    /**
     * Creates a new [HBoxBuilder] for building [HBox] components.
     * @see HBox
     */
    @JvmStatic
    fun hbox() = HBoxBuilder.newBuilder()

    /**
     * Creates a new [VBoxBuilder] for building [VBox] components.
     * @see VBox
     */
    @JvmStatic
    fun vbox() = VBoxBuilder.newBuilder()

    /**
     * Creates a new [HorizontalSliderBuilder] for building [Slider] components.
     * @see Slider
     */
    @JvmStatic
    fun horizontalSlider() = HorizontalSliderBuilder.newBuilder()

    /**
     * Creates a new [VerticalSliderBuilder] for building [Slider] components.
     * @see Slider
     */
    @JvmStatic
    fun verticalSlider() = VerticalSliderBuilder.newBuilder()

    /**
     * Creates a new [HorizontalScrollBarBuilder] for building horizontal [ScrollBar] components.
     * @see ScrollBar
     */
    @JvmStatic
    fun horizontalScrollbar() = HorizontalScrollBarBuilder.newBuilder()

    /**
     * Creates a new [VerticalScrollBarBuilder] for building vertical [ScrollBar] components.
     * @see ScrollBar
     */
    @JvmStatic
    fun verticalScrollbar() = VerticalScrollBarBuilder.newBuilder()

    /**
     * Creates a new [RadioButtonBuilder] for building [RadioButton] components.
     * @see RadioButton
     */
    @JvmStatic
    fun radioButton() = RadioButtonBuilder.newBuilder()

    /**
     * Creates a new [RadioButtonGroupBuilder] for building [RadioButtonGroup]s.
     * Note that a [RadioButtonGroup] is not a [Component], but a logical grouping.
     * @see RadioButtonGroup
     */
    @JvmStatic
    fun radioButtonGroup() = RadioButtonGroupBuilder.newBuilder()

    /**
     * Creates a new [GroupBuilder] for building [Group]s.
     * Note that a [Group] is not a [Component], but a logical grouping.
     * @see Group
     */
    @JvmStatic
    fun <T : Component> group() = GroupBuilder.newBuilder<T>()


}
