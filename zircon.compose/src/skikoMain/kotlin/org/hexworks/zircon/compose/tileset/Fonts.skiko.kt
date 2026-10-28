package org.hexworks.zircon.compose.tileset

import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.createFontFamilyResolver
import androidx.compose.ui.text.platform.Font
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.LayoutDirection

internal actual fun fontFamilyFromBytes(name: String, bytes: ByteArray): FontFamily =
    FontFamily(Font(identity = name, data = bytes))

internal actual fun createTextMeasurer(): TextMeasurer =
    TextMeasurer(createFontFamilyResolver(), Density(1f), LayoutDirection.Ltr)
