package org.hexworks.zircon.api

import org.hexworks.zircon.api.uievent.*
import kotlin.jvm.JvmStatic

object UIEventResponses {

    /**
     * [Pass] is the default [UIEventResponse] which indicates
     * that no event processing happened.
     */
    @JvmStatic
    @Deprecated("Use UIEventResponse.pass instead", replaceWith = ReplaceWith(
            "UIEventResponse.pass", "org.hexworks.zircon.api.uievent.UIEventResponse"))
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
    @Deprecated("Use UIEventResponse.processed instead", replaceWith = ReplaceWith(
            "UIEventResponse.processed", "org.hexworks.zircon.api.uievent.UIEventResponse"))
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
    @Deprecated("Use UIEventResponse.preventDefault instead", replaceWith = ReplaceWith(
            "UIEventResponse.preventDefault", "org.hexworks.zircon.api.uievent.UIEventResponse"))
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
    @Deprecated("Use UIEventResponse.stopPropagation instead", replaceWith = ReplaceWith(
            "UIEventResponse.stopPropagation", "org.hexworks.zircon.api.uievent.UIEventResponse"))
    fun stopPropagation() = StopPropagation

}
