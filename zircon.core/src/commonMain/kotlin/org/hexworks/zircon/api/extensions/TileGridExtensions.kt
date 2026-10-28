package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.internal.extensions.asInternal
import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.screen.TileGridScreen

public fun TileGrid.toScreen(): Screen = TileGridScreen(asInternal())