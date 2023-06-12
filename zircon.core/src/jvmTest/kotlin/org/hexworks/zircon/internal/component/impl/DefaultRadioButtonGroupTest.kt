package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.builder.component.buildRadioButton
import org.junit.Before
import org.junit.Test

class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup

    @Before
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

        assertThat(btn.isSelected).isTrue()
        assertThat(target.selectedButton).isEqualTo(btn)
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


        assertThat(oldBtn.isSelected).isFalse()
        assertThat(newBtn.isSelected).isTrue()
        assertThat(target.selectedButton).isEqualTo(newBtn)
    }

}
