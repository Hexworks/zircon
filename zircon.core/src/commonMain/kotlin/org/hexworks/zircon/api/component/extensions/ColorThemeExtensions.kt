package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ColorTheme.Companion.UNKNOWN

val ColorTheme.isUnknown: Boolean
    get() = this === UNKNOWN

val ColorTheme.isNotUnknown: Boolean
    get() = isUnknown.not()
