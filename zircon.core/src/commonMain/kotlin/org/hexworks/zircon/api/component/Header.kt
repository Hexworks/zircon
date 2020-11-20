package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [Header] is a non-interactive [Component] that semantically represents
 * a header. It also contains style information that's consistent with its
 * purpose and the other semantic elements, like:
 * - [Paragraph]
 * - [ListItem]
 * - [Label]
 * - [Icon]
 */
interface Header : Component, TextOverride
