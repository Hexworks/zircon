package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.cobalt.databinding.api.value.UpdateOnBind
import org.hexworks.zircon.api.builder.component.ProgressBarBuilder
import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.component.ProgressBar
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.component.theme.ComponentFactory
import org.hexworks.zircon.api.component.theme.ComponentRole
import org.hexworks.zircon.api.component.theme.Part
import org.hexworks.zircon.api.component.theme.ComponentTheme
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.fragment.ProgressField
import kotlin.math.roundToInt
import org.hexworks.zircon.api.behavior.extensions.height
import org.hexworks.zircon.api.behavior.extensions.width

public class DefaultProgressField internal constructor(
    width: Int,
    label: String,
    range: Int,
    initialProgress: Double,
    showPercent: Boolean,
    theme: ComponentTheme?,
) : ProgressField {

    private val components = ComponentFactory { theme }
    private val labelComponent: Label
    override val progressBar: ProgressBar
    override val root: VBox

    override var label: String
        get() = labelComponent.text
        set(value) {
            labelComponent.text = value
        }

    init {
        val barBuilder = components.configure(ProgressBarBuilder(), ComponentRole.PROGRESS_BAR)
        val inner = maxOf(1, width - barBuilder.decorationsSize.width)
        progressBar = barBuilder.apply {
            this.range = range
            numberOfSteps = minOf(range, inner)
            preferredContentSize = Size(inner, 1)
        }.build()
        progressBar.progress = initialProgress
        val percentWidth = if (showPercent) PERCENT_WIDTH else 0
        labelComponent = components.label {
            role = ComponentRole.PROGRESS_FIELD
            part = Part.LABEL
            preferredSize = Size(maxOf(1, progressBar.width - percentWidth - 1), 1)
            +label
        }
        val headerParts = mutableListOf<Component>(labelComponent)
        if (showPercent) {
            headerParts += components.label {
                role = ComponentRole.PROGRESS_FIELD
            part = Part.VALUE
                preferredSize = Size(percentWidth, 1)
                textProperty.updateFrom(progressBar.progressProperty, UpdateOnBind) { progress ->
                    val percent = ((progress.coerceIn(0.0, range.toDouble()) / range) * 100).roundToInt()
                    "$percent%".padStart(percentWidth)
                }
            }
        }
        val header = components.hBox {
            role = ComponentRole.PROGRESS_FIELD
            part = Part.HEADER
            spacing = 1
            preferredContentSize = Size(progressBar.width, 1)
            childrenToAdd = headerParts
        }
        root = components.vBox {
            role = ComponentRole.PROGRESS_FIELD
            preferredContentSize = Size(progressBar.width, 1 + progressBar.height)
            childrenToAdd = listOf(header, progressBar)
        }
    }

    private companion object {
        const val PERCENT_WIDTH = 4
    }
}
