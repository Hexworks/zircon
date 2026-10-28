package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.graphics.Layer

public fun Layer.hide() {
    isHidden = true
}

public fun Layer.show() {
    isHidden = false
}
