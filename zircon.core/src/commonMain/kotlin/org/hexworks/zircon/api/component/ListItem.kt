package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [ListItem] is a non-interactive [Component] that semantically represents
 * a textual list item. It also contains style information that's consistent with its
 * purpose and the other semantic elements, like:
 * - [Paragraph]
 * - [Header]
 * - [Label]
 * - [Icon]
 */
interface ListItem : Component, TextOverride
