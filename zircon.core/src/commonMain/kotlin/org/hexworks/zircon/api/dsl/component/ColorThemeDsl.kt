package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.ColorThemeBuilder
import org.hexworks.zircon.api.component.ColorTheme

/**
 * Creates a new [ColorTheme] using the component builder DSL and returns it.
 */
fun colorTheme(init: ColorThemeBuilder.() -> Unit): ColorTheme =
    ColorThemeBuilder().apply(init).build()
