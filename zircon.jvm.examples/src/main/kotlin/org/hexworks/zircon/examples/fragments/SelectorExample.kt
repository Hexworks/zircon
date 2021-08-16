package org.hexworks.zircon.examples.fragments

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

object SelectorExample {

    private val theme = ColorThemes.letThemEatCake()

    @JvmStatic
    fun main(args: Array<String>) {
        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withSize(Size.create(60, 40))
                .withBorderless(true)
                .withDefaultTileset(CP437TilesetResources.wanderlust16x16())
                .build()
        )

        val screen = Screen.create(tileGrid)
        screen.theme = theme

        val leftPanel =
            Components.panel().withPreferredSize(20, 40).withAlignmentWithin(screen, ComponentAlignment.LEFT_CENTER)
                .withDecorations(ComponentDecorations.box(BoxType.SINGLE, "Try them!")).build().also {
                    screen.addComponent(it)
                }

        val fragmentsList = Components.vbox()
            .withPreferredSize(leftPanel.contentSize.width, 20)
            .withAlignmentWithin(leftPanel, ComponentAlignment.CENTER)
            .withSpacing(2)
            .build().also {
                leftPanel.addComponent(it)
            }

        val logArea =
            Components.logArea().withPreferredSize(40, 40).withAlignmentWithin(screen, ComponentAlignment.RIGHT_CENTER)
                .withDecorations(ComponentDecorations.box(BoxType.TOP_BOTTOM_DOUBLE, "Logs")).build().also {
                    screen.addComponent(it)
                }

        val width = fragmentsList.contentSize.width

        fragmentsList.addFragment(
            Fragments.selector<String>()
                .withWidth(width)
                .withValues(listOf("Centered", "strings", "as", "values").toProperty()).build()
        )

        fragmentsList.addFragment(
            Fragments.selector<String>()
                .withWidth(width)
                .withValues(listOf("Strings", "left", "aligned").toProperty())
                .withCenteredText(false)
                .build()
        )

        fragmentsList.addFragment(
            Fragments.selector<String>()
                .withWidth(width)
                .withValues(listOf("Long", "values", "get", "truncated and that's it").toProperty())
                .build()
        )

        // This is a special form of MultiSelect
        fragmentsList.addFragment(
            Fragments.colorThemeSelector(width, theme).withThemeOverrides(screen).build()
        )

        fragmentsList.addFragment(
            Fragments.selector<Int>()
                .withWidth(width)
                .withValues(listOf(2, 4, 8, 16, 32).toProperty())
                .build().apply {
                    selectedValue.onChange { (oldValue, newValue) ->
                        logArea.addParagraph("Changed value from $oldValue to $newValue", true)
                    }
                }
        )

        fragmentsList.addFragment(
            Fragments.selector<String>()
                .withWidth(width)
                .withValues(listOf("Click", "me!").toProperty())
                .withClickableLabel(true)
                .build().apply {
                    selectedValue.onChange { (oldValue, newValue) ->
                        val text = if (oldValue == newValue) {
                            "You clicked the label!"
                        } else {
                            "You changed from '$oldValue' to '$newValue'. Try clicking the label!"
                        }
                        logArea.addParagraph(text, true)
                    }
                }
        )

        screen.display()
    }
}
