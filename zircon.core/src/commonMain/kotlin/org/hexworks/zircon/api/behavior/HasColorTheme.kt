package org.hexworks.zircon.api.behavior

import org.hexworks.zircon.api.component.ColorTheme

/**
 * Represents an object that has a [ColorTheme].
 */
public interface HasColorTheme {

    public val theme: ColorTheme
}
