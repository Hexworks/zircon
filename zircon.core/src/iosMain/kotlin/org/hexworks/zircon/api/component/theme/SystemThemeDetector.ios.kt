package org.hexworks.zircon.api.component.theme

/**
 * A library cannot read the trait collection without a view; the app passes the mode in.
 */
public actual fun platformSystemThemeDetector(): SystemThemeDetector = SystemThemeDetector.NONE
