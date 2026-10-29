package org.hexworks.zircon.terminal

import org.hexworks.zircon.api.data.Size

/**
 * The terminal device: raw mode, size, bytes in and out, and the signals the loop must
 * react to. [systemTty] gives the real one; tests substitute their own.
 */
public interface Tty {

    /** Switches the terminal to raw mode (no echo, no line buffering, no signal keys). */
    public fun open()

    /** Restores the mode the terminal had before [open]. Safe to call more than once. */
    public fun close()

    /** The terminal size in cells. */
    public fun size(): Size

    /**
     * Reads what is available into [buffer], waiting at most [timeoutMillis] for the first
     * byte. Returns the number of bytes read, `0` on timeout, or `-1` when the input is gone.
     */
    public fun read(buffer: ByteArray, timeoutMillis: Int): Int

    /** Writes the first [length] bytes of [bytes] and flushes. */
    public fun write(bytes: ByteArray, length: Int)

    /** `true` once since the last call when the terminal was resized. */
    public fun takeResize(): Boolean

    /** `true` once the process was asked to stop (SIGTERM, SIGHUP). */
    public val terminateRequested: Boolean
}

/** The process's own terminal. */
public expect fun systemTty(): Tty
