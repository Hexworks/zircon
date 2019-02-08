package org.hexworks.zircon.internal.component

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.component.Container

/**
 * A [InternalContainer] is a specialization of the [Component] interface which adds
 * functionality which will be used by Zircon internally. This makes it possible to have
 * a clean API for [Container]s but enables Zircon and the developers of custom [Container]s
 * to interact with them in a more meaningful manner.
 */
interface InternalContainer : Container, InternalComponent
