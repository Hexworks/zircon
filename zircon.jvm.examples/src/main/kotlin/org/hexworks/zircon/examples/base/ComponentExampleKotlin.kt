package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.Components.hbox
import org.hexworks.zircon.api.Components.header
import org.hexworks.zircon.api.Components.label
import org.hexworks.zircon.api.Components.panel
import org.hexworks.zircon.api.Components.vbox
import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.Fragments.tilesetSelector
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig.Companion.newBuilder
import org.hexworks.zircon.api.component.Container
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Position.Companion.offset1x1
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Size.Companion.create
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.screen.Screen.Companion.create
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class ComponentExampleKotlin(
        private val size: Size = GRID_SIZE
) {

    /**
     * Creates the container for the examples.
     */
    fun createExampleContainer(screen: Screen, title: String): VBox {

        val container = vbox()
                .withSize(size)
                .withSpacing(1)
                .withComponentRenderer(NoOpComponentRenderer())
                .withTileset(DEFAULT_TILESET)
                .build()

        val heading = hbox()
                .withSize(size.width, 5)
                .build()

        val controls = vbox()
                .withSize(size.width / 2, 5)
                .build()

        controls.addComponent(label().withText("Pick a theme"))

        controls.addFragment(Fragments.colorThemeSelector(controls.width -4, DEFAULT_THEME.getTheme())
                .withThemeables(screen)
                .build())

        controls.addComponent(label())

        controls.addComponent(label().withText("Pick a tileset"))
        val tilesetSelector = tilesetSelector(controls.width, DEFAULT_TILESET)
                .withTilesetOverrides(container)
                .build()
        controls.addFragment(tilesetSelector)
        heading.addComponents(
                header().withText(title).withSize(size.width / 2, 1).build(),
                controls)
        val exampleArea = hbox()
                .withComponentRenderer(NoOpComponentRenderer())
                .withSize(size.width, size.height - 6)
                .build()
        addExamples(exampleArea)
        container.addComponents(heading, exampleArea)
        return container
    }

    /**
     * Shows this example with the given title on the screen.
     */
    fun show(title: String) {
        val tileGrid = startTileGrid(newBuilder()
                .withDefaultTileset(DEFAULT_TILESET)
                .withSize(size.plus(create(2, 2)))
                .build())
        val screen = create(tileGrid)
        val container: Container = panel()
                .withSize(size)
                .withPosition(offset1x1())
                .withComponentRenderer(NoOpComponentRenderer())
                .build()
        container.addComponent(createExampleContainer(screen, title))
        screen.addComponent(container)
        screen.display()
        screen.theme = DEFAULT_THEME.getTheme()
    }

    /**
     * Builds the actual example code using the given box.
     */
    abstract fun build(box: VBox)

    /**
     * Adds the example(s) to the root container according to the layout
     * being used (one column vs two column).
     */
    abstract fun addExamples(exampleArea: HBox)

}
