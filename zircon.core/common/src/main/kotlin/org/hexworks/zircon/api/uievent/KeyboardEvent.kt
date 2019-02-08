package org.hexworks.zircon.api.uievent

data class KeyboardEvent(override val type: KeyboardEventType,
                         val key: String,
                         val code: KeyCode,
                         val ctrlDown: Boolean = false,
                         val altDown: Boolean = false,
                         val metaDown: Boolean = false,
                         val shiftDown: Boolean = false) : UIEvent
