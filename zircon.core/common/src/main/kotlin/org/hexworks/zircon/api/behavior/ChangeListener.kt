package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.databinding.api.event.ChangeEvent

interface ChangeListener<T : Any> {

    fun onChange(event: ChangeEvent<T>)
}
