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
     * Creates a new [HorizontalNumberInputBuilder] for building horizontal [NumberInput] [Component]s.
     * @see NumberInput
     */
    @JvmStatic
    fun horizontalNumberInput(width: Int) = HorizontalNumberInputBuilder.newBuilder(width)


    /**
     * Creates a new [VerticalNumberInputBuilder] for building vertical [NumberInput] [Component]s.
     * @see NumberInput
     */
    @JvmStatic
    fun verticalNumberInput(height: Int) = VerticalNumberInputBuilder.newBuilder(height)

    /**
     * Creates a new [LogAreaBuilder] for building [LogArea] [Component]s.
     * @see LogArea
     */
    @JvmStatic
    fun logArea() = LogAreaBuilder.newBuilder()

    /**
     * Creates a new [TextBoxBuilder] for building [TextBox] [Component]s.
     * @see TextBox
     */
    @JvmStatic
    fun textBox(contentWidth: Int) = TextBoxBuilder.newBuilder(contentWidth)

    /**
     * Creates a new [PanelBuilder] for building [Panel] [Component]s.
     * @see Panel
     */
    @JvmStatic
    fun panel() = PanelBuilder.newBuilder()

    /**
     * Creates a new [LabelBuilder] for building [Label] [Component]s.
     * @see Label
     */
    @JvmStatic
    fun label() = LabelBuilder.newBuilder()

    /**
     * Creates a new [ButtonBuilder] for building [Button] [Component]s.
     * @see Button
     */
    @JvmStatic
    fun button() = ButtonBuilder.newBuilder()

    /**
     * Creates a new [ToggleButtonBuilder] for building [ToggleButton] [Component]s.
     * @see ToggleButton
     */
    @JvmStatic
    fun toggleButton() = ToggleButtonBuilder.newBuilder()

    /**
     * Creates a new [CheckBoxBuilder] for building [CheckBox] [Component]s.
     * @see CheckBox
     */
    @JvmStatic
    fun checkBox() = CheckBoxBuilder.newBuilder()

    /**
     * Creates a new [ProgressBarBuilder] for building [ProgressBar] [Component]s.
     * @see ProgressBar
     */
    @JvmStatic
    fun progressBar() = ProgressBarBuilder.newBuilder()

    /**
     * Creates a new [HeaderBuilder] for building [Header] [Component]s.
     * @see Header
     */
    @JvmStatic
    fun header() = HeaderBuilder.newBuilder()

    /**
     * Creates a new [ParagraphBuilder] for building [Paragraph] [Component]s.
     * @see Paragraph
     */
    @JvmStatic
    fun paragraph() = ParagraphBuilder.newBuilder()

    /**
     * Creates a new [ListItemBuilder] for building [ListItem] [Component]s.
     * @see ListItem
     */
    @JvmStatic
    fun listItem() = ListItemBuilder.newBuilder()

    /**
     * Creates a new [IconBuilder] for building [Icon] [Component]s.
     * @see Icon
     */
    @JvmStatic
    fun icon() = IconBuilder.newBuilder()

    /**
     * Creates a new [HBoxBuilder] for building [HBox] [Component]s.
     * @see HBox
     */
    @JvmStatic
    fun hbox() = HBoxBuilder.newBuilder()

    /**
     * Creates a new [VBoxBuilder] for building [VBox] [Component]s.
     * @see VBox
     */
    @JvmStatic
    fun vbox() = VBoxBuilder.newBuilder()

    /**
     * Creates a new [HorizontalSliderBuilder] for building [Slider] [Component]s.
     * @see Slider
     */
    @JvmStatic
    fun horizontalSlider() = HorizontalSliderBuilder.newBuilder()

    /**
     * Creates a new [VerticalSliderBuilder] for building [Slider] [Component]s.
     * @see Slider
     */
    @JvmStatic
    fun verticalSlider() = VerticalSliderBuilder.newBuilder()

    /**
     * Creates a new [HorizontalScrollBarBuilder] for building horizontal [ScrollBar] [Component]s.
     * @see ScrollBar
     */
    @JvmStatic
    fun horizontalScrollbar() = HorizontalScrollBarBuilder.newBuilder()

    /**
     * Creates a new [VerticalScrollBarBuilder] for building vertical [ScrollBar] [Component]s.
     * @see ScrollBar
     */
    @JvmStatic
    fun verticalScrollbar() = VerticalScrollBarBuilder.newBuilder()

    /**
     * Creates a new [RadioButtonBuilder] for building [RadioButton] [Component]s.
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

    /**
     * Creates a new [GameComponentBuilder] for building [GameComponent] [Component]s.
     * @see GameComponent
     * **Note that** [GameComponent] is in **Beta**.
     */
    @Beta
    @JvmStatic
    fun <T : Tile, B : Block<T>> gameComponent() = GameComponentBuilder.newBuilder<T, B>()

}
