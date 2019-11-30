package org.hexworks.zircon.api.uievent

import kotlin.jvm.JvmStatic

/**
 * Represents the possible responses to an [UIEvent].
 */
sealed class UIEventResponse(
        /**
         * Tells whether the [UIEvent] had any effect on
         * the underlying system or not.
         */
        val eventProcessed: Boolean,
        /**
         * How important the given response is compared to
         * the others.
         */
        private val precedence: Int) {

    fun hasPrecedenceOver(other: UIEventResponse) = this.precedence > other.precedence

    fun shouldStopPropagation() = this == StopPropagation

    fun allowsDefaults() = this.hasPrecedenceOver(Processed).not()

    /**
     * Picks the [UIEventResponse] which has higher precedence from this
     * one and the [other].
     */
    fun pickByPrecedence(other: UIEventResponse): UIEventResponse {
        return if (hasPrecedenceOver(other)) this else other
    }

    companion object {

        /**
         * [Pass] is the default [UIEventResponse] which indicates
         * that no event processing happened.
         */
        @JvmStatic
        fun pass() = Pass

        /**
         * [Processed] indicates that at least one event handler
         * processed the [UIEvent] but it did not stop propagation
         * nor the default actions.
         *
         * Return this object if you acted on an event but you don't
         * want to tamper with other listeners.
         */
        @JvmStatic
        fun processed() = Processed

        /**
         * [PreventDefault] is an [UIEventResponse] which requests
         * the prevention of any default actions for the given event.
         *
         * Return this object if you acted on an event and you
         * don't want any default actions to take place (hover effects
         * on components for example).
         */
        @JvmStatic
        fun preventDefault() = PreventDefault

        /**
         * [StopPropagation] will not only prevent default actions
         * but stops propagation altogether so the event won't reach
         * any other listener.
         *
         * Return this object if you acted on an event and you
         * don't want any other listeners to have the chance of
         * handling it.
         */
        @JvmStatic
        fun stopPropagation() = StopPropagation
    }
}

/**
 * [Pass] is the default [UIEventResponse] which indicates
 * that no event processing happened.
 */
object Pass : UIEventResponse(false, 0)

/**
 * [Processed] indicates that at least one event handler
 * processed the [UIEvent] but it did not stop propagation
 * nor the default actions.
 *
 * Return this object if you acted on an event but you don't
 * want to tamper with other listeners.
 */
object Processed : UIEventResponse(true, 1)

/**
 * [PreventDefault] is an [UIEventResponse] which requests
 * the prevention of any default actions for the given event.
 *
 * Return this object if you acted on an event and you
 * don't want any default actions to take place (hover effects
 * on components for example).
 */
object PreventDefault : UIEventResponse(true, 2)

/**
 * [StopPropagation] will not only prevent default actions
 * but stops propagation altogether so the event won't reach
 * any other listener.
 *
 * Return this object if you acted on an event and you
 * don't want any other listeners to have the chance of
 * handling the event.
 */
object StopPropagation : UIEventResponse(true, 3)


