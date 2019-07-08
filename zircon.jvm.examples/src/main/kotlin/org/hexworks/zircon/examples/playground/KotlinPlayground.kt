@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.AppConfigs
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Screens
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.extensions.box
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.internal.component.renderer.NoOpComponentRenderer

object KotlinPlayground {

    private val theme = ColorThemes.solarizedLightOrange()
    private val tileset = CP437TilesetResources.rexPaint16x16()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .enableBetaFeatures()
                .withDefaultTileset(tileset)
                .build())


        val screen = Screens.createScreenFor(tileGrid)


        val content = Components.panel()
                .withSize(40, 23)
                .withDecorations(box(title = "Pick Tile and Color", boxType = BoxType.DOUBLE))
                .build().apply {
                    applyColorTheme(ColorThemes.adriftInDreams())
                    addComponent(Components.hbox()
                            .withSpacing(1)
                            .withSize(38, 21)
                            .build().apply {
                                applyColorTheme(ColorThemes.afterTheHeist())
                                addComponent(Components.panel()
                                        .withDecorations(box(title = "Tile"))
                                        .withPosition(Position.offset1x1())
                                        .withSize(18, 18)
                                        .withComponentRenderer(NoOpComponentRenderer())
                                        .build().apply {
                                            applyColorTheme(ColorThemes.afterglow())
                                        })

                                addComponent(Components.panel()
                                        .withDecorations(box(title = "Color"))
                                        .withPosition(Position.offset1x1())
                                        .withSize(18, 18)
                                        .build().apply {
                                            applyColorTheme(ColorThemes.amigaOs())
                                            addComponent(Components.panel()
                                                    .withSize(16, 16)
                                                    .withComponentRenderer(NoOpComponentRenderer())
                                                    .build().apply {
                                                        applyColorTheme(ColorThemes.ancestry())
                                                    })
                                        })

                            })
                }

        content.addComponent(Components.button().withText("OK")
                .withAlignmentWithin(content, ComponentAlignment.BOTTOM_CENTER)
                .build().apply {
                    applyColorTheme(ColorThemes.arc())
                })
        screen.addComponent(content)

        screen.display()
    }
}
