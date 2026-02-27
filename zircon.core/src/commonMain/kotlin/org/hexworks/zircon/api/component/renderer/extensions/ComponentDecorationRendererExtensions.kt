package org.hexworks.zircon.api.component.renderer.extensions

import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.BOTTOM_CENTER
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.BOTTOM_LEFT
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.BOTTOM_RIGHT
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.TOP_CENTER
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.TOP_LEFT
import org.hexworks.zircon.api.component.renderer.ComponentDecorationRenderer.Alignment.TOP_RIGHT

internal fun Alignment.isLeft() = when (this) {
    TOP_LEFT, BOTTOM_LEFT -> true
    else -> false
}

internal fun Alignment.isRight() = when (this) {
    TOP_RIGHT, BOTTOM_RIGHT -> true
    else -> false
}

internal fun Alignment.isCenter() = when (this) {
    TOP_CENTER, BOTTOM_CENTER -> true
    else -> false
}

internal fun Alignment.isTop() = when (this) {
    TOP_LEFT, TOP_RIGHT, TOP_CENTER -> true
    else -> false
}

internal fun Alignment.isBottom() = when (this) {
    BOTTOM_LEFT, BOTTOM_RIGHT, BOTTOM_CENTER -> true
    else -> false
}
