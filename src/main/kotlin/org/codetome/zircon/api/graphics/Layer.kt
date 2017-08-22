package org.codetome.zircon.api.graphics

import org.codetome.zircon.api.behavior.Layerable
import org.codetome.zircon.api.behavior.Movable
import org.codetome.zircon.api.graphics.TextImage

/**
 * A [Layer] is a specialized [TextImage] which is drawn upon a
 * [Layerable] object. A [Layer] differs from a [TextImage] in
 * the way it is handled. It can be repositioned relative to its
 * parent while a [TextImage] cannot.
 */
interface Layer : TextImage, Movable