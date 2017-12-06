package org.codetome.zircon.api.beta.component

/**
 * Represents the size of a slice of 3D space. Extends [org.codetome.zircon.api.Size]
 * with a `levels` dimension.
 */
data class Size3D(val columns: Int, val rows : Int, val levels : Int)