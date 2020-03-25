package org.hexworks.zircon.api.uievent

data class KeyboardEventMatcher(
        val type: KeyboardEventType? = null,
        val key: String? = null,
        val code: KeyCode? = null,
        val ctrlDown: Boolean? = null,
        val altDown: Boolean? = null,
        val metaDown: Boolean? = null,
        val shiftDown: Boolean? = null
) {

    fun matches(event: KeyboardEvent): Boolean {
        var result = true
        type?.let {
            result = result && it == event.type
        }
        key?.let {
            result = result && it == event.key
        }
        code?.let {
            result = result && it == event.code
        }
        ctrlDown?.let {
            result = result && it == event.ctrlDown
        }
        altDown?.let {
            result = result && it == event.altDown
        }
        metaDown?.let {
            result = result && it == event.metaDown
        }
        shiftDown?.let {
            result = result && it == event.shiftDown
        }
        return result
    }

    companion object {

        fun create(
                type: KeyboardEventType? = null,
                key: String? = null,
                code: KeyCode? = null,
                ctrlDown: Boolean? = null,
                altDown: Boolean? = null,
                metaDown: Boolean? = null,
                shiftDown: Boolean? = null
        ) = KeyboardEventMatcher(
                type = type,
                key = key,
                code = code,
                ctrlDown = ctrlDown,
                altDown = altDown,
                metaDown = metaDown,
                shiftDown = shiftDown)
    }

}
