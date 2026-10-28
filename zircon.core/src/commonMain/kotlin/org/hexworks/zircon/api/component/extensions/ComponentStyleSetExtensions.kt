package org.hexworks.zircon.api.component.extensions

import org.hexworks.zircon.api.component.ComponentStyleSet
import org.hexworks.zircon.api.component.ComponentStyleSet.Companion.UNKNOWN

public val ComponentStyleSet.isStyleUnknown: Boolean
    get() = this === UNKNOWN

public val ComponentStyleSet.isStyleNotUnknown: Boolean
    get() = isStyleUnknown.not()