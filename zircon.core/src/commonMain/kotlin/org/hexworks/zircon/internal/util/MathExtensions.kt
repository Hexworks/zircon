package org.hexworks.zircon.internal.util

import kotlin.math.ceil
import kotlin.math.floor

internal fun Double.toIntCeil(): Int = ceil(this).toInt()

internal fun Double.toIntFloor(): Int = floor(this).toInt()

internal val Int.isOdd: Boolean get() = this % 2 != 0
