package org.hexworks.zircon.integration

import org.hexworks.zircon.api.Components
import org.hexworks.zircon.api.Positions
import org.hexworks.zircon.api.Sizes
import org.hexworks.zircon.api.graphics.BoxType
import org.hexworks.zircon.api.screen.Screen

class RadioButtonGroupIntegrationTest : ComponentIntegrationTestBase() {

    override fun buildScreenContent(screen: Screen) {
        val panel = Components.panel()
                .wrapWithBox(true)
                .withSize(Sizes.create(28, 28))
                .withPosition(Positions.create(31, 1))
                .build()
        screen.addComponent(panel)

        val simpleRadioButtonGroup0 = Components.radioButtonGroup()
                .withPosition(Positions.create(2, 2))
                .withSize(Sizes.create(14, 3))
                .build()
        val simpleRadioButtonGroup1 = Components.radioButtonGroup()
                .withPosition(Positions.create(0, 0))
                .withSize(Sizes.create(14, 3))
                .build()

        listOf(simpleRadioButtonGroup0, simpleRadioButtonGroup1).forEach {
            it.addOption("key0", "First")
            it.addOption("key1", "Second")
            it.addOption("key2", "Third")
        }

        screen.addComponent(simpleRadioButtonGroup0)
        panel.addComponent(simpleRadioButtonGroup1)

        val decoratedRadioButtonGroup0 = Components.radioButtonGroup()
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withSize(Sizes.create(14, 3))
                .withPosition(Positions.create(2, 8))
                .build()
        val decoratedRadioButtonGroup1 = Components.radioButtonGroup()
                .withBoxType(BoxType.DOUBLE)
                .wrapWithShadow(true)
                .wrapWithBox(true)
                .withSize(Sizes.create(14, 3))
                .withPosition(Positions.create(0, 6))
                .build()

        screen.addComponent(decoratedRadioButtonGroup0)
        panel.addComponent(decoratedRadioButtonGroup1)

        listOf(decoratedRadioButtonGroup0, decoratedRadioButtonGroup1).forEach {
            it.addOption("key0", "First")
            it.addOption("key1", "Second")
            it.addOption("key2", "Third")
        }
    }
}
