@file:Suppress("DuplicatedCode")

package org.hexworks.zircon.examples.playground

import org.hexworks.cobalt.databinding.api.extension.toProperty
import org.hexworks.zircon.api.ColorThemes
import org.hexworks.zircon.api.ComponentAlignments
import org.hexworks.zircon.api.ComponentDecorations.box
import org.hexworks.zircon.api.SwingApplications
import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.component.ComponentAlignment
import org.hexworks.zircon.api.component.Label
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.dsl.component.*
import org.hexworks.zircon.api.dsl.fragment.table
import org.hexworks.zircon.api.extensions.toScreen
import org.hexworks.zircon.api.extensions.useComponentBuilder

object KotlinPlayground {

    @JvmStatic
    fun main(args: Array<String>) {

        data class Person(
            val name: String,
            val age: Int,
            val occupation: String
        )

        val persons = listOf(
            Person(
                name = "Joe",
                age = 28,
                occupation = "programmer"
            ), Person(
                name = "Jane",
                age = 30,
                occupation = "UX Expert"
            ), Person(
                name = "Tanya",
                age = 26,
                occupation = "Commando"
            )
        ).toProperty()

        SwingApplications.startTileGrid().toScreen().apply {
            display()
        }.useComponentBuilder {
            table<Person> {
                data = persons
                textColumn {
                    name = "Name"
                    width = 10
                    valueProvider = Person::name
                }
                numberColumn {
                    name = "Age"
                    width = 4
                    valueProvider = Person::age
                }
                textColumn {
                    name = "Occupation"
                    width = 15
                    valueProvider = Person::occupation
                }
            }
        }
    }
}

