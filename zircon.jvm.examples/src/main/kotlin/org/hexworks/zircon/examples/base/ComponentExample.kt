package org.hexworks.zircon.examples.base

import org.hexworks.zircon.api.Fragments
import org.hexworks.zircon.api.SwingApplications.startTileGrid
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.component.HBox
import org.hexworks.zircon.api.component.VBox
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.buildVbox
import org.hexworks.zircon.api.dsl.component.hbox
import org.hexworks.zircon.api.dsl.component.header
import org.hexworks.zircon.api.dsl.component.label
import org.hexworks.zircon.api.dsl.component.vbox
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.examples.extensions.colorThemeSelector
import org.hexworks.zircon.examples.extensions.tilesetSelector
import org.hexworks.zircon.examples.extensions.updateFromSelection
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

abstract class ComponentExample {

    /**
     * Creates the container for the examples.
     */
    fun createExampleContainer(screen: Screen, title: String) = buildVbox {
        val container = this
        val size = screen.size
        preferredSize = size
        spacing = 1
        componentRenderer = NoOpComponentRenderer()
        tileset = screen.tileset

        hbox {
            name = "Heading"
            preferredSize = Size.create(size.width, 5)

            header {
                +title
                preferredSize = Size.create(size.width / 2, 1)
            }

            vbox {
                name = "Controls"
                preferredSize = Size.create(size.width / 2, 5)

                label { +"Pick a theme" }
                withAddedChildren(
                    Fragments.colorThemeSelector()
                        .withWidth(preferredSize.width - 4)
                        .withDefaultSelected(screen.theme)
                        .build()
                        .updateFromSelection(screen.themeProperty)
                        .root
                )

                label { }

                label { +"Pick a tileset" }

                withAddedChildren(
                    Fragments.tilesetSelector()
                        .withWidth(preferredSize.width - 4)
                        .withDefaultSelected(screen.tileset)
                        .build()
                        .updateFromSelection(container.tilesetProperty)
                        .root
                )
            }
        }

        hbox {
            name = "Example Area"
            componentRenderer = NoOpComponentRenderer()
            preferredSize = Size.create(size.width, size.height - 6)
        }.apply {
            addExamples(this)
        }
    }

    /**
     * Shows this example with the given title on the screen.
     */
    fun show(
        title: String,
        size: Size = GRID_SIZE
    ) {
        startTileGrid(
            AppConfig.newBuilder()
                .withDefaultTileset(DEFAULT_TILESET)
                .withSize(size)
                .build()
        ).toScreen().apply {
            theme = DEFAULT_THEME
            addComponent(createExampleContainer(this, title))
            display()
        }
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
