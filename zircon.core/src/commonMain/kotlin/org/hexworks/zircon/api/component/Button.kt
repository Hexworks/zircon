package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.Disablable
import org.hexworks.zircon.api.behavior.TextHolder

/**
 * Generic clickable *button* component.
 */
interface Button : Component, TextHolder, Disablable
