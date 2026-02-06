package org.hexworks.zircon.internal.util.rex

import org.hexworks.zircon.api.color.Color

/**
 * Represents a CP437 character on a REX Paint [REXLayer].
 */
data class REXCell(
    val character: Char,
    val foregroundColor: Color,
    val backgroundColor: Color
)
