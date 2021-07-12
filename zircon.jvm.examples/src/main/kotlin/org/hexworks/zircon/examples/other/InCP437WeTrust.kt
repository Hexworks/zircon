package org.hexworks.zircon.examples.other


import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.ComponentDecorations.shadow
import org.hexworks.zircon.api.application.AppConfig
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.graphics.Symbols
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.api.tileset.impl.CP437TileMetadataLoader
import org.hexworks.zircon.api.uievent.MouseEventType
import java.util.*
import kotlin.reflect.full.declaredMemberProperties

object InCP437WeTrust {

    private val theme = ColorThemes.solarizedLightCyan()

    // Pick a tileset here, it may be of any size
    private val startingTileset = CP437TilesetResources.msGothic16x16()

    private val symbolsMap: Map<Char, String> =
        Symbols::class.declaredMemberProperties.associate { it.getter.call() as Char to it.name }
    private val currentSymbol = ' '.toProperty()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(
            AppConfig.newBuilder()
                .withSize(Size.create(23, 28))
                .withDefaultTileset(startingTileset)
                .build()
        )

        val screen = Screen.create(tileGrid)

        val loader = CP437TileMetadataLoader(16, 16)

        val cp437panel = Components.panel()
            .withSize(Size.create(19, 18))
            .withDecorations(box(BoxType.SINGLE), shadow())
            .withRendererFunction { tileGraphics, _ ->
                loader.fetchMetadata().forEach { (char, meta) ->
                    tileGraphics.draw(
                        tile = Tile.defaultTile()
                            .withCharacter(char)
                            .withBackgroundColor(theme.primaryBackgroundColor)
                            .withForegroundColor(ANSITileColor.values()[Random().nextInt(ANSITileColor.values().size)]),
                        drawPosition = Position.create(meta.x, meta.y)
                    )
                }
            }.build()
            .apply {
                processMouseEvents(MouseEventType.MOUSE_MOVED) { event, _ ->
                    val mousePos = event.position - (contentOffset + position)
                    loader
                        .fetchMetadata()
                        .values
                        .filter { it.x == mousePos.x && it.y == mousePos.y }
                        .map { it.character }
                        .firstOrNull()
                        ?.let { currentSymbol.updateValue(it) }
                }
            }

        val mainPanel = Components.vbox()
            .withPreferredSize(19, 19)
            .withPosition(2, 1)
            .build()
            .apply {
                addComponent(Components.panel()
                    .withPreferredSize(19, 1)
                    .build()
                    .apply {
                        addFragment(
                            Fragments.tilesetSelector(contentSize.width, tileset)
                                .withTilesetOverrides(cp437panel)
                                .build()
                        )
                    })

                addComponent(cp437panel)
            }

        screen.addComponent(mainPanel)

        val btn = Components.checkBox()
            .withText("In CP437 we trust!")
            .withPreferredSize(23, 1)
            .withPosition(Position.bottomLeftOf(mainPanel) - Position.create(2, 0))
            .build()

        screen.addComponent(btn)

        // mouse over for the current symbol
        screen.addComponent(Components.vbox()
            .withSpacing(0)
            .withPreferredSize(21, 3)
            .withPosition(Position.bottomLeftOf(btn) + Position.create(1, 1))
            .build()
            .apply {
                addComponent(Components.label().withText("Symbols."))
                addComponent(symbolResolver(0))
                addComponent(symbolResolver(20))
            })

        screen.theme = theme

        screen.display()
    }

    private fun symbolResolver(subStringStart: Int) =
        Components
            .label()
            .withPreferredSize(21, 1)
            .build()
            .apply {
                textProperty.updateFrom(
                    currentSymbol.bindTransform {
                        val symbolName = symbolsMap[it] ?: "(not special)".padEnd(21) + it.toString()
                        if (symbolName.length > subStringStart) {
                            symbolName.substring(subStringStart)
                        } else {
                            ""
                        }
                    },
                    true
                )
            }
}
