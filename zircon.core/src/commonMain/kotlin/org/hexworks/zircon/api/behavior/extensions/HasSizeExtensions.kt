package org.hexworks.zircon.api.behavior.extensions

import org.hexworks.zircon.api.behavior.HasSize

public val HasSize.width: Int
    get() = size.width

public val HasSize.height: Int
    get() = size.height