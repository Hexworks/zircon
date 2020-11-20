package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [Paragraph] is a non-interactive [Component] that semantically represents
 * a paragraph of text. It also contains style information that's consistent with its
 * purpose and the other semantic elements, like:
 * - [ListItem]
 * - [Header]
 * - [Label]
 * - [Icon]
 */
interface Paragraph : Component, TextOverride
