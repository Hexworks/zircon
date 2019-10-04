package org.hexworks.zircon.internal.screen

import org.hexworks.zircon.api.screen.Screen
import org.hexworks.zircon.internal.component.InternalComponentContainer
import org.hexworks.zircon.internal.uievent.UIEventProcessor

/**
 * Represents the internal API of a [Screen]
 */
interface InternalScreen : Screen, InternalComponentContainer, UIEventProcessor
