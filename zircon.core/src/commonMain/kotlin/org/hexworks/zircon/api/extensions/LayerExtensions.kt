package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.graphics.Layer

fun Layer.hide() {
    isHidden = true
}

fun Layer.show() {
    isHidden = false
}
