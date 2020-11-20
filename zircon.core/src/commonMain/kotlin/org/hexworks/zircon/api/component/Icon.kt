package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.data.Tile

/**
 * An [Icon] is a non-interactive [Component] that semantically represents
 * an icon. It also contains style information that's consistent with its
 * purpose and the other semantic elements, like:
 * - [Paragraph]
 * - [ListItem]
 * - [Header]
 * - [Label]
 * *Note that* any kind of [Tile] can be used as the [icon] including
 * graphical tiles.
 */
interface Icon : Component {
    var icon: Tile
    val iconProperty: Property<Tile>
}
