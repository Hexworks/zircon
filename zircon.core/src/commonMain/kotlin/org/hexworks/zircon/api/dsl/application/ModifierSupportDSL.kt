package org.hexworks.zircon.api.dsl.application

import org.hexworks.zircon.api.application.ModifierSupport
import org.hexworks.zircon.api.builder.application.ModifierSupportBuilder
import org.hexworks.zircon.api.builder.application.TilesetFactoryBuilder
import org.hexworks.zircon.api.tileset.TilesetFactory

/**
 * Creates a new [ModifierSupport] using the builder DSL and returns it.
 */
fun <T : Any> buildModifierSupport(init: ModifierSupportBuilder<T>.() -> Unit): ModifierSupport<T> =
    ModifierSupportBuilder.newBuilder<T>().apply(init).build()

