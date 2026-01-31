package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [Label] is a non-interactive [Component] that semantically represents
 * a label. It also contains style information that's consistent with its
 * purpose and the other semantic elements, like:
 * - [Paragraph]
 * - [ListItem]
 * - [Header]
 * - [Icon]
 * The semantic difference between a [Label] and a [Paragraph] is that a [Label]
 * contains a single line of text.
 */
interface Label : Component, TextOverride
