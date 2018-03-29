package org.codetome.zircon.api.color

import org.codetome.zircon.internal.behavior.Cacheable

interface TextColor : Cacheable {

    fun toAWTColor(): java.awt.Color

    fun getAlpha(): Int

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int

    fun tint(): TextColor

    fun shade(): TextColor

    fun invert(): TextColor
}