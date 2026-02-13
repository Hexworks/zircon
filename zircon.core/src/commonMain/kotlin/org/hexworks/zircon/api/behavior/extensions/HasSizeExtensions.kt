package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.HasSize

val HasSize.width: Int
    get() = size.width

val HasSize.height: Int
    get() = size.height