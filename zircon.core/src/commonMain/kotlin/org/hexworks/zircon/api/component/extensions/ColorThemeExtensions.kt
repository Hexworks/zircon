package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.component.ColorTheme
import org.hexworks.zircon.api.component.ColorTheme.Companion.UNKNOWN

val ColorTheme.isColorUnknown: Boolean
    get() = this === UNKNOWN

val ColorTheme.isColorNotUnknown: Boolean
    get() = isColorUnknown.not()
