package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.grid.TileGrid
import org.hexworks.zircon.api.screen.Screen

fun TileGrid.toScreen() = Screen.create(this)