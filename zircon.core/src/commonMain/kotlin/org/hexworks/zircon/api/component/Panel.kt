package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.ComponentDecorations
import org.hexworks.zircon.api.behavior.TitleOverride

/**
 * A simple [Container] for [Component]s that can contain a [title]
 * and doesn't align its children.
 * Note that the [title] will only be visible if the [Panel] is decorated with
 * a box.
 * @see ComponentDecorations
 * @see Container
 */
interface Panel : Container, TitleOverride
