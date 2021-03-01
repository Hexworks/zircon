package org.hexworks.zircon.examples.fragments.table

import org.hexworks.cobalt.databinding.api.property.Property

data class Person(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val gender: Gender,
    val wage: Property<Int>
) {
    companion object {
        val MIN_WAGE = 10000
        val MAX_WAGE = 90500
    }
}