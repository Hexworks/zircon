package org.hexworks.zircon.internal.component.impl

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.api.CP437TilesetResources
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.Components
import org.junit.Before
import org.junit.Test

@Suppress("MemberVisibilityCanBePrivate", "UNCHECKED_CAST")
class DefaultRadioButtonGroupTest {

    lateinit var target: DefaultRadioButtonGroup

    @Before
    fun setUp() {
        target = DefaultRadioButtonGroup(
                initialIsDisabled = false,
                initialIsHidden = false,
                initialTheme = ColorThemes.afterglow(),
                initialTileset = CP437TilesetResources.bisasam16x16())
    }

    @Test
    fun shouldSelectButtonWhenClicked() {

        val btn = Components.radioButton()
                .withKey("qux")
                .withText("baz")
                .build() as (DefaultRadioButton)
        target.addComponent(btn)

        btn.activated()

        assertThat(btn.isSelected).isTrue()
        assertThat(target.selectedButton.get()).isEqualTo(btn)
    }


    @Test
    fun shouldProperlyDeselectPreviouslySelectedButtonWhenNewOneIsSelected() {
        val oldBtn = Components.radioButton()
                .withKey("old")
                .withText("old")
                .build() as (DefaultRadioButton)
        val newBtn = Components.radioButton()
                .withKey("new")
                .withText("new")
                .build() as (DefaultRadioButton)
        oldBtn.isSelected = true
        newBtn.isSelected = true
        target.addComponents(oldBtn, newBtn)


        assertThat(oldBtn.isSelected).isFalse()
        assertThat(newBtn.isSelected).isTrue()
        assertThat(target.selectedButton.get()).isEqualTo(newBtn)
    }

}
