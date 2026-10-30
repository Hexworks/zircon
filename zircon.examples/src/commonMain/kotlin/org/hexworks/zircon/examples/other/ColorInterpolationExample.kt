package org.hexworks.zircon.examples.other

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.builder.component.button
import org.hexworks.zircon.api.builder.component.horizontalNumberInput
import org.hexworks.zircon.api.builder.component.horizontalSlider
import org.hexworks.zircon.api.builder.component.icon
import org.hexworks.zircon.api.builder.component.label
import org.hexworks.zircon.api.builder.component.panel
import org.hexworks.zircon.api.color.Color
import org.hexworks.zircon.api.color.extensions.interpolateTo
import org.hexworks.zircon.api.color.extensions.withBlue
import org.hexworks.zircon.api.color.extensions.withGreen
import org.hexworks.zircon.api.color.extensions.withRed
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Icon
import org.hexworks.zircon.api.component.NumberInput
import org.hexworks.zircon.api.component.builder.base.decorations
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

/**
 * Two colours picked with number inputs, interpolated along a slider. The alpha inputs of
 * the old example are gone: 2.0 colours have no alpha.
 */
public val colorInterpolationExample: Example = Example("Color interp") { context ->
    val root = panel {
        preferredSize = context.contentSize
        componentRenderer = NoOpComponentRenderer()
    }
    val panel = panel {
        decorations {
            +box(title = "Interpolations")
            +shadow()
        }
        preferredSize = Size(34, 30)
    }
    root.addComponent(panel)

    val lowColorProperty = Color.create(255, 0, 0).toProperty()
    val highColorProperty = Color.create(0, 255, 0).toProperty()
    var interpolator = lowColorProperty.value.interpolateTo(highColorProperty.value)

    panel.addComponent(label { +"Red"; position = Position(6, 2) })
    panel.addComponent(label { +"Green"; position = Position(11, 2) })
    panel.addComponent(label { +"Blue"; position = Position(16, 2) })
    val colorOneLabel = label { +"Color1"; position = Position(0, 4) }
    val colorTwoLabel = label { +"Color2"; position = Position(0, 6) }
    panel.addComponent(colorOneLabel)
    panel.addComponent(colorTwoLabel)

    fun channelInput(value: Int, rightOf: org.hexworks.zircon.api.component.Component): NumberInput = horizontalNumberInput {
        initialValue = value
        maxValue = 255
        decorations { }
        alignAround(rightOf, ComponentAlignment.RIGHT_CENTER)
    }
    val redOne = channelInput(255, colorOneLabel)
    val greenOne = channelInput(0, redOne)
    val blueOne = channelInput(0, greenOne)
    val redTwo = channelInput(0, colorTwoLabel)
    val greenTwo = channelInput(255, redTwo)
    val blueTwo = channelInput(0, greenTwo)

    fun swatch(color: Color, rightOf: org.hexworks.zircon.api.component.Component): Icon = icon {
        iconTile = Tile.defaultTile().withBackgroundColor(color)
        alignAround(rightOf, ComponentAlignment.RIGHT_CENTER)
    }
    val iconOne = swatch(lowColorProperty.value, blueOne)
    val iconTwo = swatch(highColorProperty.value, blueTwo)

    val newInterpolation = button {
        +"New interpolation"
        position = Position(7, 9)
    }.apply {
        onActivated {
            // The interpolator is created here
            interpolator = lowColorProperty.value.interpolateTo(highColorProperty.value)
        }
    }
    val slider = horizontalSlider {
        minValue = 0
        maxValue = 1000
        numberOfSteps = 20
        decorations { }
        position = Position(6, 11)
    }
    val resultPanel = panel {
        preferredSize = Size(7, 7)
        position = Position(13, 15)
        decorations { +box() }
        componentRenderer = NoOpComponentRenderer()
    }
    (0 until 25).forEach { i ->
        resultPanel.addComponent(icon {
            position = Position(i % 5, i / 5)
            iconTile = Tile.defaultTile()
        }.also { cell ->
            cell.iconProperty.updateFrom(slider.currentValueProperty) { sliderValue ->
                // Interpolated value is used here
                (cell.icon as CharacterTile).withBackgroundColor(interpolator.getColorAtRatio(sliderValue / 1000.0))
            }
        })
    }

    lowColorProperty.updateFrom(redOne.currentValueProperty) { lowColorProperty.value.withRed(it) }
    lowColorProperty.updateFrom(greenOne.currentValueProperty) { lowColorProperty.value.withGreen(it) }
    lowColorProperty.updateFrom(blueOne.currentValueProperty) { lowColorProperty.value.withBlue(it) }
    highColorProperty.updateFrom(redTwo.currentValueProperty) { highColorProperty.value.withRed(it) }
    highColorProperty.updateFrom(greenTwo.currentValueProperty) { highColorProperty.value.withGreen(it) }
    highColorProperty.updateFrom(blueTwo.currentValueProperty) { highColorProperty.value.withBlue(it) }
    iconOne.iconProperty.updateFrom(lowColorProperty) { (iconOne.icon as CharacterTile).withBackgroundColor(it) }
    iconTwo.iconProperty.updateFrom(highColorProperty) { (iconTwo.icon as CharacterTile).withBackgroundColor(it) }

    listOf(redOne, greenOne, blueOne, redTwo, greenTwo, blueTwo, iconOne, iconTwo, newInterpolation, slider, resultPanel)
        .forEach { panel.addComponent(it) }
    ExampleView(root)
}
