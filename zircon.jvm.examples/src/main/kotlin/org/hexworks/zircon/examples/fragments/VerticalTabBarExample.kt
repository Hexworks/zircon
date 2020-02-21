package org.hexworks.zircon.examples.fragments


import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.examples.base.Defaults
import org.hexworks.zircon.examples.components.ButtonsExampleJava
import org.hexworks.zircon.examples.components.CheckBoxesExampleJava
import org.hexworks.zircon.examples.components.ToggleButtonsExampleJava
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer
import org.hexworks.zircon.internal.fragment.impl.DefaultVerticalTabBar

object VerticalTabBarExample {

    private val tileset = CP437TilesetResources.taffer20x20()
    private val size = Defaults.GRID_SIZE + Size.create(12, 2)

    @JvmStatic
    fun main(args: Array<String>) {

        val screen = SwingApplications.startTileGrid(AppConfig.newBuilder()
                .withDefaultTileset(tileset)
                .withSize(size)
                .withDebugMode(true)
                .build()).toScreen()

        val contentSize = screen.size - Size.create(2, 2)

        val content = Components.panel()
                .withSize(contentSize)
                .withPosition(1, 1)
                .withComponentRenderer(NoOpComponentRenderer())
                .build()

        content.addFragment(DefaultVerticalTabBar(
                contentSize = contentSize.withRelativeWidth(-10),
                barSize = contentSize.withWidth(10),
                defaultSelected = "Buttons",
                tabs = mapOf(
                        "Buttons" to ButtonsExampleJava().createExampleContainer(screen, "Buttons"),
                        "CheckBoxes" to CheckBoxesExampleJava().createExampleContainer(screen, "CheckBoxes"),
                        "ToggleBtns" to ToggleButtonsExampleJava().createExampleContainer(screen, "ToggleBtns")
                )))

        screen.addComponent(content)
        screen.display()
        screen.theme = ColorThemes.amigaOs()

    }

}












