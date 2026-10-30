package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.ComponentAlignments.alignmentWithin
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.builder.component.hBox
import org.hexworks.zircon.api.builder.component.logArea
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.builder.component.vBox
import org.hexworks.zircon.api.builder.fragment.selector
import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.component.extensions.addParagraph
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.examples.THEMES

/**
 * Selectors cycle through a list of values with two arrow buttons: centred or left-aligned
 * text, truncated long values, a theme picker that re-themes the screen, numbers, and a
 * clickable label. Every change is logged on the right.
 */
public val selectorExample: Example = Example("Selector") { context ->
    val size = context.contentSize
    val screen = context.screen
    val root = hBox { preferredSize = size }

    val leftPanel = panel {
        preferredSize = Size(LEFT_WIDTH, size.height)
        decorations { +box(BoxType.SINGLE, "Try them!") }
    }
    root.addComponent(leftPanel)

    val logArea = logArea {
        preferredSize = Size(size.width - LEFT_WIDTH, size.height)
        decorations { +box(BoxType.TOP_BOTTOM_DOUBLE, "Logs") }
    }
    root.addComponent(logArea)

    val fragmentsList = vBox {
        preferredSize = Size(leftPanel.contentSize.width, 20)
        alignment = alignmentWithin(leftPanel, ComponentAlignment.CENTER)
        spacing = 2
    }
    leftPanel.addComponent(fragmentsList)
    val width = fragmentsList.contentSize.width

    fragmentsList.addComponent(selector<String> {
        this.width = width
        valueList = listOf("Centered", "strings", "as", "values")
    }.root)

    fragmentsList.addComponent(selector<String> {
        this.width = width
        valueList = listOf("Strings", "left", "aligned")
        centeredText = false
    }.root)

    fragmentsList.addComponent(selector<String> {
        this.width = width
        valueList = listOf("Long", "values", "get", "truncated and that's it")
    }.root)

    // Picking a theme here re-themes the whole screen.
    fragmentsList.addComponent(selector<ColorTheme> {
        this.width = width
        valueList = THEMES
        defaultSelected = screen.theme
        toStringMethod = { it.name }
    }.apply {
        selectedProperty.onChange { (oldValue, newValue) ->
            screen.theme = newValue
            logArea.addParagraph("Changed value from ${oldValue.name} to ${newValue.name}")
        }
    }.root)

    fragmentsList.addComponent(selector<Int> {
        this.width = width
        valueList = listOf(2, 4, 8, 16, 32)
    }.apply {
        selectedProperty.onChange { (oldValue, newValue) ->
            logArea.addParagraph("Changed value from $oldValue to $newValue")
        }
    }.root)

    fragmentsList.addComponent(selector<String> {
        this.width = width
        valueList = listOf("Click", "me!")
        clickableLabel = true
    }.apply {
        selectedProperty.onChange { (oldValue, newValue) ->
            val text = if (oldValue == newValue) {
                "You clicked the label!"
            } else {
                "You changed from '$oldValue' to '$newValue'. Try clicking the label!"
            }
            logArea.addParagraph(text)
        }
    }.root)

    ExampleView(root)
}

private const val LEFT_WIDTH = 24
