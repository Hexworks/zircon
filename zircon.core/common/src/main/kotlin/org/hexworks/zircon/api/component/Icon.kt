package org.hexworks.zircon.api.component

import org.hexworks.cobalt.databinding.api.property.Property
import org.hexworks.zircon.api.data.GraphicTile
import org.hexworks.zircon.api.data.Tile

interface Icon : Component {

    var icon: Tile
    val iconProperty: Property<Tile>
}
