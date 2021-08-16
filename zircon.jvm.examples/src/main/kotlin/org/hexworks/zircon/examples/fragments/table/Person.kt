package org.hexworks.zircon.examples.fragments.table

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.builder.data.TileBuilder
import org.hexworks.zircon.api.color.ANSITileColor
import org.hexworks.zircon.api.graphics.Symbols

data class Person(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val height: Height,
    val wage: Property<Int>
) {

    val formattedWage: ObservableValue<String> = wage.bindTransform {
        val thousands = it / 1000
        val remainder = it % 1000
        "$thousands,${remainder.toString().padStart(3, '0')} $"
    }

    val heightIcon
        get() = TileBuilder
            .newBuilder()
            .withForegroundColor(
                when (height) {
                    Height.TALL -> ANSITileColor.BLUE
                    Height.SHORT -> ANSITileColor.RED
                }
            )
            .withBackgroundColor(ANSITileColor.WHITE)
            .withCharacter(
                when (height) {
                    Height.TALL -> Symbols.TRIANGLE_UP_POINTING_BLACK
                    Height.SHORT -> Symbols.TRIANGLE_DOWN_POINTING_BLACK
                }
            )
            .buildCharacterTile()

    companion object {
        val MIN_WAGE = 10000
        val MAX_WAGE = 90500
    }
}