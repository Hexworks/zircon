package org.codetome.zircon.graphics.shape

/**
 * How a drawing operation should behave when
 * the drawable object overflows the target [org.codetome.zircon.DrawSurface].
 */
enum class DrawOverflowStrategy {
    IGNORE_OVERFLOW,
    EXCEPTION_ON_OVERFLOW
}