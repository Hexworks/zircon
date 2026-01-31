package org.hexworks.zircon.api.modifier

import org.hexworks.zircon.api.data.Tile

/**
 * A [TextureModifier] is a kind of [Modifier]
 * that transforms the actual texture represented by
 * a [Tile] before rendering.
 */
interface TextureModifier : Modifier
