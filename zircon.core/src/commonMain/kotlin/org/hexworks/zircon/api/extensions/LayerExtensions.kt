@file:JvmName("LayerUtils")

package org.hexworks.zircon.api.extensions

import org.hexworks.zircon.api.graphics.Layer
import kotlin.jvm.JvmName

fun Layer.hide() {
    isHidden = true
}

fun Layer.show() {
    isHidden = false
}
