@file:Suppress("UNUSED_VARIABLE")

package org.hexworks.zircon.examples.playground

import org.hexworks.zircon.api.*
import org.hexworks.zircon.api.builder.component.ModalBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.internal.component.modal.EmptyModalResult

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        val s = Screens.createScreenFor(LibgdxApplications.startTileGrid(
                AppConfigs.newConfig()
                        .enableBetaFeatures()
                        .withDefaultTileset(CP437TilesetResources.acorn8X16())
                        .withSize(Sizes.create(180, 60))
                        .build()))

        s.addComponent(Components.panel().withSize(14, 10).wrapWithBox().withTitle("regular").build())

        val modal = ModalBuilder<EmptyModalResult>()
                .withComponent(Components
                        .panel().withSize(120, 50).withTitle("modal").build().apply {
                            addComponent(Components.panel().withSize(120, 5).wrapWithBox().build().apply {
                                addComponent(Components.textBox().withContentWidth(60)
                                        .addParagraph("hjkl or arrow keys to move cursor", withNewLine = false)
                                        .addParagraph("1-9 to select square for active tab. 0 for inventory", withNewLine = false)
                                        .addParagraph("(or GHJKLYUBNI"))
                                addComponent(Components.textBox().withPosition(60, 0).withContentWidth(60)
                                        .addParagraph("[m]ove item between screen", withNewLine = false)
                                        .addParagraph("[e]xamine item", withNewLine = false)
                                        .addParagraph("[q]uit/exit this screen"))
                            })
                            addComponent(Components.panel().withSize(60, 45).wrapWithBox().withAlignmentWithin(this, ComponentAlignment.BOTTOM_LEFT).build().apply {
                                addComponent(Components.header().withText("Inventory"))
                                addComponent(Components.radioButtonGroup().withPosition(0, 2).withSize(58, 40).build().apply {
                                    addOption("0", "Name")
                                    addOption("1", "sinew")
                                    addOption("2", "pinlock kit")
                                })
                            })
                            addComponent(Components.panel().withSize(60, 45).wrapWithBox().withAlignmentWithin(this, ComponentAlignment.BOTTOM_RIGHT).build().apply {
                                addComponent(Components.label().withText("Directly below you"))
                                addComponent(Components.radioButtonGroup().withPosition(0, 2).withSize(58, 40).build().apply {
                                    addOption("0", "aluminium keg")
                                    addOption("1", "solar cell")
                                    addOption("2", "cigarettes (10)")
                                })
                            })
                            applyColorTheme(ColorThemes.cyberpunk())
                        })
                .withParentSize(s.size)
                .build()

        s.openModal(modal)

        s.display()
        s.applyColorTheme(ColorThemes.arc())

    }

}
