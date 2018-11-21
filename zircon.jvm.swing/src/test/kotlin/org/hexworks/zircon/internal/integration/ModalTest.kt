@file:Suppress("UNCHECKED_CAST")

package org.hexworks.zircon.internal.integration

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.data.ComponentMetadata
import org.hexworks.zircon.api.component.modal.Modal
import org.hexworks.zircon.api.component.modal.ModalResult
import org.hexworks.zircon.api.component.renderer.ComponentRenderer
import org.hexworks.zircon.api.component.renderer.impl.DefaultComponentRenderingStrategy
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.kotlin.onClosed
import org.hexworks.zircon.api.kotlin.onMouseReleased
import org.hexworks.zircon.internal.component.modal.DefaultModal
import org.hexworks.zircon.internal.component.modal.EmptyModalResult
import org.hexworks.zircon.internal.component.renderer.DefaultModalRenderer

object ModalTest {

    private val theme = ColorThemes.arc()
    private val tileset = CP437TilesetResources.rexPaint20x20()

    @JvmStatic
    fun main(args: Array<String>) {

        val tileGrid = SwingApplications.startTileGrid(AppConfigs.newConfig()
                .withDefaultTileset(tileset)
                .withSize(Sizes.create(60, 30))
                .build())

        val screen = Screens.createScreenFor(tileGrid)

        val renderer: ComponentRenderer<Modal<out ModalResult>> = DefaultModalRenderer() as  ComponentRenderer<Modal<out ModalResult>>
        val modal = DefaultModal<ModalResult>(
                componentMetadata = ComponentMetadata(
                        position = Position.defaultPosition(),
                        size = tileGrid.size,
                        tileset = tileset,
                        componentStyleSet = ComponentStyleSet.empty()),
                renderingStrategy = DefaultComponentRenderingStrategy(
                        componentRenderer = renderer))

        val modalPanel = Components.panel()
                .withSize(Sizes.create(20, 20))
                .withPosition(Positions.create(20, 5))
                .wrapWithBox()
                .wrapWithShadow()
                .withTitle("Confirm")
                .build()

        modal.addComponent(modalPanel)

        modalPanel.addComponent(Components.textBox()
                .withContentWidth(16)
                .withPosition(Positions.create(1, 1))
                .addParagraph("By clicking OK you confirm to close this modal window.")
                .build())

        val confirmButton = Components.button()
                .withText("OK")
                .withPosition(Positions.create(12, 16))
                .build()

        val openModalButton = Components.button()
                .withText("Open modal!")
                .withPosition(Positions.create(20, 20))
                .build()

        modalPanel.addComponent(confirmButton)

        openModalButton.onMouseReleased {
            screen.openModal(modal)
        }

        confirmButton.onMouseReleased {
            modal.close(EmptyModalResult)
        }

        modal.onClosed { result ->

        }


        screen.addComponent(Components.textBox()
                .withContentWidth(18)
                .addParagraph("foo")
                .addNewLine()
                .addParagraph("Test paragraph")
                .build())

        screen.addComponent(Components.panel()
                .withSize(Sizes.create(10, 10))
                .withPosition(Positions.create(20, 1))
                .withTitle("Title")
                .wrapWithBox()
                .wrapWithShadow()
                .build())

        screen.addComponent(openModalButton)

        screen.display()
        screen.applyColorTheme(theme)
        modal.applyColorTheme(theme)
    }

}
