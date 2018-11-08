package org.hexworks.zircon.api.kotlin

import org.hexworks.zircon.api.data.Cell

fun Iterable<Cell>.toMap() = map { (pos, tile) -> pos to tile }.toMap()
