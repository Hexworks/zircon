package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.behavior.Cacheable
import org.hexworks.zircon.api.data.Tile
import org.hexworks.zircon.api.grid.TileGrid

/**
 * A [Modifier] adds an effect to a [Tile] when it is rendered on
 * a [TileGrid]. There are two kinds:
 * - [TileModifier] transforms a [Tile] to another one before rendering
 * - [TextureModifier] transforms the texture of the [Tile] before rendering.
 *
 * Use [TextureModifier]s, when you want a graphical effect, like glow, flipping,
 * cropping, etc. Use [TileModifier] when you want tile variations, or more elaborate
 * effects, like [FadeIn], [FadeOut] or [Markov].
 */
interface Modifier : Cacheable
