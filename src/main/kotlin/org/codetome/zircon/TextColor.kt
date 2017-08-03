package org.codetome.zircon

interface TextColor {

    fun toAWTColor(): java.awt.Color

    fun getAlpha(): Int

    fun getRed(): Int

    fun getGreen(): Int

    fun getBlue(): Int
}