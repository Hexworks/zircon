package org.hexworks.zircon.api.dsl.tileset

import org.hexworks.zircon.api.builder.application.TilesetFactoryBuilder
import org.hexworks.zircon.api.tileset.TilesetFactory

/**
 * Creates a new [TilesetFactory] using the builder DSL and returns it.
 */
fun <S : Any> buildTilesetFactory(init: TilesetFactoryBuilder<S>.() -> Unit): TilesetFactory<S> =
    TilesetFactoryBuilder.newBuilder<S>().apply(init).build()
