package org.hexworks.zircon.api.application

import org.hexworks.zircon.api.builder.application.AppConfigBuilder
import org.hexworks.zircon.api.builder.application.ModifierSupportBuilder
import org.hexworks.zircon.api.builder.application.TilesetFactoryBuilder
import org.hexworks.zircon.api.tileset.TilesetFactory
import java.awt.Graphics2D
import java.awt.image.BufferedImage

fun AppConfigBuilder.swingModifierSupport(init: ModifierSupportBuilder<BufferedImage>.() -> Unit): ModifierSupport<BufferedImage> {
    return buildSwingModifierSupport(init).apply {
        withModifierSupports(this)
    }
}

fun AppConfigBuilder.swingTilesetFactory(init: TilesetFactoryBuilder<Graphics2D>.() -> Unit): TilesetFactory<Graphics2D> {
    return buildSwingTilesetFactory(init).apply {
        withTilesetFactories(this)
    }
}

/**
 * Creates a new [ModifierSupport] for [BufferedImage] using the builder DSL and returns it.
 */
fun buildSwingModifierSupport(init: ModifierSupportBuilder<BufferedImage>.() -> Unit): ModifierSupport<BufferedImage> =
    ModifierSupportBuilder.newBuilder<BufferedImage>()
        .apply(init)
        .apply {
            targetType = BufferedImage::class
        }
        .build()

fun buildSwingTilesetFactory(init: TilesetFactoryBuilder<Graphics2D>.() -> Unit): TilesetFactory<Graphics2D> {
    return TilesetFactoryBuilder.newBuilder<Graphics2D>()
        .apply(init)
        .apply {
            targetType = Graphics2D::class
        }
        .build()
}
