package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A [Component] that looks like a button visually and can be activated
 * by either *clicking* on it or pressing the action key (`[Spacebar]` by default).
 */
interface Button : Component, TextOverride
