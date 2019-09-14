package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.modifier.BorderBuilder
import kotlin.jvm.JvmStatic

object Borders {

    @JvmStatic
    fun newBuilder() = BorderBuilder.newBuilder()
}
