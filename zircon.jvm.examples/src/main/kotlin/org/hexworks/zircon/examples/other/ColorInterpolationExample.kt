package org.hexworks.zircon.examples.other

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.TileColor
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

object ColorInterpolationExample {

    private val theme = ColorThemes.solarizedLightBlue()
    private val tileset = CP437TilesetResources.wanderlust16x16()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withCursorBlinking(true)
                .withSize(34, 30)
                .build()
        )

        val lowColor = TileColor.create(255, 0, 0)
        val lowColorProperty = lowColor.toProperty()

        val highColor = TileColor.create(0, 255, 0)
        val highColorProperty = highColor.toProperty()

        var interpolator = lowColor.interpolateTo(highColor)

        val screen = Screen.create(tileGrid)
        val panel = Components.panel()
            .withDecorations(box(title = "Interpolations"), shadow())
            .withPreferredSize(34, 30)
            .build()
        screen.addComponent(panel)

        val redLabel = Components.label()
            .withText("Red")
            .withPosition(6, 2)
            .build()
        val greenLabel = Components.label()
            .withText("Green")
            .withPosition(11, 2)
            .build()
        val blueLabel = Components.label()
            .withText("Blue")
            .withPosition(16, 2)
            .build()
        val alphaLabel = Components.label()
            .withText("Alpha")
            .withPosition(21, 2)
            .build()

        val colorOneLabel = Components.label()
            .withText("Color1")
            .withPosition(0, 4)
            .build()
        val colorTwoLabel = Components.label()
            .withText("Color2")
            .withPosition(0, 6)
            .build()

        val redOneInput = Components.horizontalNumberInput()
            .withInitialValue(255)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(colorOneLabel, ComponentAlignment.RIGHT_CENTER)
            .build()
        val greenOneInput = Components.horizontalNumberInput()
            .withInitialValue(0)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(redOneInput, ComponentAlignment.RIGHT_CENTER)
            .build()
        val blueOneInput = Components.horizontalNumberInput()
            .withInitialValue(0)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(greenOneInput, ComponentAlignment.RIGHT_CENTER)
            .build()
        val alphaOneInput = Components.horizontalNumberInput()
            .withInitialValue(255)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(blueOneInput, ComponentAlignment.RIGHT_CENTER)
            .build()

        val redTwoInput = Components.horizontalNumberInput()
            .withInitialValue(0)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(colorTwoLabel, ComponentAlignment.RIGHT_CENTER)
            .build()
        val greenTwoInput = Components.horizontalNumberInput()
            .withInitialValue(255)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(redTwoInput, ComponentAlignment.RIGHT_CENTER)
            .build()
        val blueTwoInput = Components.horizontalNumberInput()
            .withInitialValue(0)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(greenTwoInput, ComponentAlignment.RIGHT_CENTER)
            .build()
        val alphaTwoInput = Components.horizontalNumberInput()
            .withInitialValue(255)
            .withMaxValue(255)
            .withDecorations()
            .withAlignmentAround(blueTwoInput, ComponentAlignment.RIGHT_CENTER)
            .build()

        val iconColorOne = Components.icon()
            .withIcon(Tile.defaultTile().withBackgroundColor(lowColorProperty.value))
            .withAlignmentAround(alphaOneInput, ComponentAlignment.RIGHT_CENTER)
            .build()
        val iconColorTwo = Components.icon()
            .withIcon(Tile.defaultTile().withBackgroundColor(highColorProperty.value))
            .withAlignmentAround(alphaTwoInput, ComponentAlignment.RIGHT_CENTER)
            .build()

        val button = Components.button()
            .withText("New interpolation")
            .withPosition(7, 9)
            .build().apply {
                onActivated {
                    // The interpolator is created here
                    interpolator = lowColorProperty.value.interpolateTo(highColorProperty.value)
                }
            }

        val slider = Components.horizontalSlider()
            .withMinValue(0)
            .withMaxValue(1000)
            .withNumberOfSteps(20)
            .withDecorations()
            .withPosition(6, 11)
            .build()

        val resultPanel = Components.panel()
            .withPreferredSize(7, 7)
            .withDecorations()
            .withPosition(13, 15)
            .withDecorations(box())
            .withComponentRenderer(NoOpComponentRenderer())
            .build()

        (0 until 25).forEach {
            resultPanel.addComponent(Components.icon()
                .withPosition((it % 5), (it / 5))
                .withIcon(Tile.defaultTile())
                .build().also { icon ->
                    icon.iconProperty.updateFrom(slider.currentValueProperty) { sliderValue ->
                        val ratio = sliderValue / 1000.0
                        // Interpolated value is used here
                        icon.iconProperty.value.withBackgroundColor(interpolator.getColorAtRatio(ratio))
                    }
                })
        }

        lowColorProperty.updateFrom(redOneInput.currentValueProperty) {
            lowColorProperty.value.withRed(it)
        }
        lowColorProperty.updateFrom(greenOneInput.currentValueProperty) {
            lowColorProperty.value.withGreen(it)
        }
        lowColorProperty.updateFrom(blueOneInput.currentValueProperty) {
            lowColorProperty.value.withBlue(it)
        }
        lowColorProperty.updateFrom(alphaOneInput.currentValueProperty) {
            lowColorProperty.value.withAlpha(it)
        }

        highColorProperty.updateFrom(redTwoInput.currentValueProperty) {
            highColorProperty.value.withRed(it)
        }
        highColorProperty.updateFrom(greenTwoInput.currentValueProperty) {
            highColorProperty.value.withGreen(it)
        }
        highColorProperty.updateFrom(blueTwoInput.currentValueProperty) {
            highColorProperty.value.withBlue(it)
        }
        highColorProperty.updateFrom(alphaTwoInput.currentValueProperty) {
            highColorProperty.value.withAlpha(it)
        }

        iconColorOne.iconProperty.updateFrom(lowColorProperty) {
            iconColorOne.icon.withBackgroundColor(it)
        }
        iconColorTwo.iconProperty.updateFrom(highColorProperty) {
            iconColorTwo.icon.withBackgroundColor(it)
        }

        panel.addComponent(redLabel)
        panel.addComponent(greenLabel)
        panel.addComponent(blueLabel)
        panel.addComponent(alphaLabel)
        panel.addComponent(colorOneLabel)
        panel.addComponent(colorTwoLabel)
        panel.addComponent(redOneInput)
        panel.addComponent(greenOneInput)
        panel.addComponent(blueOneInput)
        panel.addComponent(alphaOneInput)
        panel.addComponent(redTwoInput)
        panel.addComponent(greenTwoInput)
        panel.addComponent(blueTwoInput)
        panel.addComponent(alphaTwoInput)
        panel.addComponent(iconColorOne)
        panel.addComponent(iconColorTwo)
        panel.addComponent(button)
        panel.addComponent(slider)
        panel.addComponent(resultPanel)

        screen.display()
        screen.theme = theme
    }
}
