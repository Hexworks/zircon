package org.hexworks.zircon.examples.fragments

import org.hexworks.cobalt.databinding.api.binding.bindTransform
import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.cobalt.databinding.api.value.ObservableValue
import org.hexworks.zircon.api.builder.data.characterTile
import org.hexworks.zircon.api.builder.data.withStyleSet
import org.hexworks.zircon.api.color.ANSIColor
import org.hexworks.zircon.api.color.palette.ansi.DefaultAnsiPalette
import org.hexworks.zircon.api.data.CharacterTile
import org.hexworks.zircon.api.graphics.Symbols
import kotlin.random.Random

/** The rows of the table example. */
public enum class Height { TALL, SHORT }

public data class Person(
    val firstName: String,
    val lastName: String,
    val age: Int,
    val height: Height,
    val wage: Property<Int>,
) {
    val formattedWage: ObservableValue<String> = wage.bindTransform {
        val thousands = it / 1000
        val remainder = it % 1000
        "$thousands,${remainder.toString().padStart(3, '0')} $"
    }

    val heightIcon: CharacterTile
        get() = characterTile {
            character = when (height) {
                Height.TALL -> Symbols.TRIANGLE_UP_POINTING_BLACK
                Height.SHORT -> Symbols.TRIANGLE_DOWN_POINTING_BLACK
            }
            withStyleSet {
                foregroundColor = DefaultAnsiPalette[
                    when (height) {
                        Height.TALL -> ANSIColor.BLUE
                        Height.SHORT -> ANSIColor.RED
                    }
                ]
                backgroundColor = DefaultAnsiPalette[ANSIColor.WHITE]
            }
        }

    public companion object {
        public const val MIN_WAGE: Int = 10000
        public const val MAX_WAGE: Int = 90500
    }
}

// A fixed seed keeps the table the same on every run.
private val random = Random(2026)

public fun randomPersons(count: Int): List<Person> = List(count) { randomPerson() }

public fun randomPerson(): Person = Person(
    firstName = FIRST_NAMES.random(random),
    lastName = LAST_NAMES.random(random),
    age = random.nextInt(18, 80),
    height = Height.entries.random(random),
    wage = randomWage().toProperty(),
)

public fun randomWage(): Int = random.nextInt(Person.MIN_WAGE, Person.MAX_WAGE)

// Some names, most of which are credited to https://github.com/imsky/wordlists
private val FIRST_NAMES = listOf(
    "Alice", "Bob", "Charlie", "Donald", "Alfred", "Barkley", "Barrymore", "Belvedere", "Bertuccio", "Brunton",
    "Bullimore", "Cadbury", "Figaro", "Jeeves", "Jenkins", "Leporello", "Merriman", "Passepartout", "Poole", "Simonides",
    "Callan", "Roland", "Saul", "Jalen", "Brycen", "Ryland", "Lawrence", "Davis", "Rowen", "Zain",
    "Ermias", "Jaime", "Duke", "Stetson", "Alec", "Yusuf", "Case", "Trenton", "Callen", "Ariel",
)

private val LAST_NAMES = listOf(
    "Abbey", "Abram", "Acker", "Adair", "Adam", "Adams", "Adamson", "Addison", "Adkins", "Aiken",
    "Akerman", "Akers", "Albert", "Albertson", "Albinson", "Alexander", "Alfredson", "Alger", "Alvin", "Anderson",
    "Andrews", "Ansel", "Appleton", "Archer", "Armistead", "Arnold", "Arrington", "Arthur", "Arthurson", "Ashworth",
    "Atkins", "Atkinson", "Austin", "Avery", "Babcock", "Bagley", "Bailey", "Baker", "Baldwin", "Bancroft",
)
