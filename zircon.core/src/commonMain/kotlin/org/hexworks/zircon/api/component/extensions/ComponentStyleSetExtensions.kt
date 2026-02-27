package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ComponentStyleSet.Companion.UNKNOWN

val ComponentStyleSet.isUnknown: Boolean
    get() = this === UNKNOWN

val ComponentStyleSet.isNotUnknown: Boolean
    get() = isUnknown.not()