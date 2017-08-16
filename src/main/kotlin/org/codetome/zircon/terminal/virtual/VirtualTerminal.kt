package org.codetome.zircon.terminal.virtual

import org.codetome.zircon.Position
import org.codetome.zircon.TextCharacter
import org.codetome.zircon.input.Input
import org.codetome.zircon.terminal.Terminal

/**
 * A virtual terminal is a kind of terminal emulator that exposes the [Terminal] interface
 * and maintains its state completely internally. The [VirtualTerminal] interface extends this interface and
 * allows you to query and modify its internals in a way you can not do with a regular terminal.
 */
interface VirtualTerminal : Terminal {

    /**
     * Adds a [Input] to the input queue of this virtual terminal.
     * This even will be read the next time either [VirtualTerminal.pollInput] or
     * [VirtualTerminal.readInput] is called, assuming there are no other events before it in the queue.
     */
    fun addInput(input: Input)

    /**
     * Returns a character from the viewport at the specified coordinates.
     */
    fun getCharacter(position: Position): TextCharacter

    /**
     * Sets the character at a given position.
     */
    fun setCharacter(position: Position, textCharacter: TextCharacter)

    /**
     * Adds a listener to receive notifications when certain events happens on the virtual terminal.
     * Notice that this is not the same as the list of [org.codetome.zircon.terminal.TerminalResizeListener],
     * but as the [VirtualTerminalListener] also allows you to listen on size changes,
     * it can be used for the same purpose.
     */
    fun addVirtualTerminalListener(listener: VirtualTerminalListener)

    /**
     * Removes a listener from this virtual terminal so it will no longer receive events.
     * Notice that this is not the same as the list of [org.codetome.zircon.terminal.TerminalResizeListener].
     */
    fun removeVirtualTerminalListener(listener: VirtualTerminalListener)

}
