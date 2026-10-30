package org.hexworks.zircon.api.config

/**
 * Receives the exceptions Zircon contains instead of letting them end the process: a UI event
 * listener, an animation frame, a component renderer or a tile draw that threw. The frame goes
 * on without the failed unit of work. Zircon logs every such failure through its own logger
 * first, then calls the handler installed as [ZirconDefaults.onError], so this is the place to
 * hand the error to a crash reporter or to show it in the UI.
 *
 * [source] names what failed, for example `UI event listener for MOUSE_CLICKED` or
 * `Tile at (x=3,y=4)`.
 *
 * A handler that throws is itself logged and ignored.
 */
public fun interface ErrorHandler {

    public fun onError(source: String, error: Throwable)

    public companion object {

        /**
         * Does nothing. The default: Zircon's own logging still happens.
         */
        public val NONE: ErrorHandler = ErrorHandler { _, _ -> }
    }
}
