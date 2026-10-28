package org.hexworks.zircon.api.uievent

public data class ComponentEventMatcher(
    val type: ComponentEventType? = null
) {

    public fun matches(event: ComponentEvent): Boolean {
        return type == event.type
    }

    public companion object {

        public fun create(
            type: ComponentEventType? = null
        ): ComponentEventMatcher = ComponentEventMatcher(
            type = type
        )
    }

}
