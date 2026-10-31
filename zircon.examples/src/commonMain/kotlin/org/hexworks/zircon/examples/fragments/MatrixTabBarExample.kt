package org.hexworks.zircon.examples.fragments

import org.hexworks.zircon.api.builder.fragment.matrixTabBar
import org.hexworks.zircon.examples.Example
import org.hexworks.zircon.examples.ExampleView

/** The vertical tabs of the matrix example; not every planet has every one. */
private val ASPECTS: List<String> = listOf("Overview", "Orbit", "Moons", "Atmosphere", "Rings", "Missions")

private fun aspectsOf(planet: String): List<String> = ASPECTS.filter { aspect ->
    when (aspect) {
        "Moons" -> planet !in setOf("Mercury", "Venus")
        "Atmosphere" -> planet !in setOf("Mercury", "Ceres", "Haumea")
        "Rings" -> planet in setOf("Jupiter", "Saturn", "Uranus", "Neptune", "Haumea")
        "Missions" -> planet !in setOf("Eris", "Haumea")
        else -> true
    }
}

public val matrixTabBarExample: Example = Example("Matrix tab bar") { context ->
    val size = context.contentSize
    val tabBar = matrixTabBar {
        this.size = size
        tabWidth = 12
        val viewSize = contentSize
        TAB_NAMES.forEach { planet -> horizontalTab { key = planet; label = planet } }
        ASPECTS.forEach { aspect -> verticalTab { key = aspect; label = aspect } }
        TAB_NAMES.forEach { planet ->
            aspectsOf(planet).forEach { aspect ->
                view {
                    horizontal = planet
                    vertical = aspect
                    content = tabContent("$planet / $aspect", viewSize)
                }
            }
        }
    }
    ExampleView(tabBar.root)
}
