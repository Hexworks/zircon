package org.codetome.zircon.api.component

import org.codetome.zircon.api.graphics.StyleSet

interface ComponentStyles {

    fun getCurrentStyle(): StyleSet

    fun mouseOver(): StyleSet

    fun activate(): StyleSet

    fun giveFocus(): StyleSet

    fun disable(): StyleSet

    fun reset(): StyleSet
}