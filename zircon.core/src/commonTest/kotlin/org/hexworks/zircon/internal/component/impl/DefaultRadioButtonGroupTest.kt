package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.component.radioButton
import kotlin.test.BeforeTest
import kotlin.test.Test

class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup

    @BeforeTest
    fun setUp() {
        target = DefaultRadioButtonGroup(
            initialIsDisabled = false,
            initialIsHidden = false,
            initialTheme = ColorThemes.afterglow(),
            initialTileset = CP437TilesetResources.bisasam16x16()
        )
    }

    @Test
    fun shouldSelectButtonWhenClicked() {

        val btn = radioButton {
            key = "qux"
            text = "baz"
        } as (DefaultRadioButton)
        target.addComponent(btn)

        // A click is a press (activation) followed by a release (deactivation) on the button.
        btn.activated()
        btn.deactivated()

        btn.isSelected shouldBe true
        target.selectedButton shouldBe btn
    }


    @Test
    fun shouldProperlyDeselectPreviouslySelectedButtonWhenNewOneIsSelected() {
        val oldBtn = radioButton {
            key = "old"
            text = "old"
        } as (DefaultRadioButton)
        val newBtn = radioButton {
            key = "new"
            text = "new"
        } as (DefaultRadioButton)
        oldBtn.isSelected = true
        newBtn.isSelected = true
        target.addComponents(oldBtn, newBtn)


        oldBtn.isSelected shouldBe false
        newBtn.isSelected shouldBe true
        target.selectedButton shouldBe newBtn
    }

}
