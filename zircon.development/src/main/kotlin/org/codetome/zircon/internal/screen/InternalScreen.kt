package org.codetome.zircon.internal.screen

import org.codetome.zircon.api.screen.Screen
import org.codetome.zircon.internal.component.InternalContainerHandler

/**
 * Represents the internal API of a [Screen]
 */
interface InternalScreen : Screen, InternalContainerHandler
