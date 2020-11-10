package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TitleOverride

/**
 * A [VBox] is a [Container] that automatically aligns its child elements
 * vertically (from top to bottom).
 * @see Container
 */
interface VBox : Container, TitleOverride
