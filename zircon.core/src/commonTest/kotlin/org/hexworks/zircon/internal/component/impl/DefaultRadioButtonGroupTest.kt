package org.hexworks.zircon.internal.component.impl

import io.kotest.matchers.shouldBe
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.component.buildRadioButton
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

        val btn = buildRadioButton {
            key = "qux"
            text = "baz"
        } as (DefaultRadioButton)
        target.addComponent(btn)

        btn.activated()

        btn.isSelected shouldBe true
        target.selectedButton shouldBe btn
    }


    @Test
    fun shouldProperlyDeselectPreviouslySelectedButtonWhenNewOneIsSelected() {
        val oldBtn = buildRadioButton {
            key = "old"
            text = "old"
        } as (DefaultRadioButton)
        val newBtn = buildRadioButton {
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
