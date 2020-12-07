package org.hexworks.zircon.api.uievent

data class ComponentEventMatcher(
    val type: ComponentEventType? = null
) {

    fun matches(event: ComponentEvent): Boolean {
        return type == event.type
    }

    companion object {

        fun create(
            type: ComponentEventType? = null
        ) = ComponentEventMatcher(
            type = type
        )
    }

}
