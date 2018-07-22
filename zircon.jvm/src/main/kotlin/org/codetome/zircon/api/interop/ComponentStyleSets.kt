package org.codetome.zircon.api.interop

import org.codetome.zircon.api.component.ComponentStyleSet
import org.codetome.zircon.api.builder.component.ComponentStyleSetBuilder

object ComponentStyleSets {

    @JvmStatic
    fun newBuilder() = ComponentStyleSetBuilder()

    @JvmStatic
    fun defaultStyleSet() = ComponentStyleSet.defaultStyleSet()
}
