package org.hexworks.zircon.api.dsl.component

import org.hexworks.zircon.api.builder.component.PanelBuilder
import org.hexworks.zircon.api.component.Panel

fun panel(): Panel =
    PanelBuilder().build()

