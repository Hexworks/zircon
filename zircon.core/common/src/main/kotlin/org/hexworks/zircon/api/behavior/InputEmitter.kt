package org.hexworks.zircon.api.behavior

import org.hexworks.cobalt.datatypes.extensions.map
import org.hexworks.cobalt.datatypes.sam.Consumer
import org.hexworks.cobalt.events.api.Subscription
import org.hexworks.zircon.api.behavior.input.KeyCombination
import org.hexworks.zircon.api.input.Input
import org.hexworks.zircon.api.input.InputType
import org.hexworks.zircon.api.input.KeyStroke
import org.hexworks.zircon.api.input.MouseAction
import org.hexworks.zircon.api.input.MouseActionType.*
import org.hexworks.zircon.api.listener.InputListener
import org.hexworks.zircon.api.listener.KeyStrokeListener
import org.hexworks.zircon.api.listener.MouseListener

/**
 * Represents an object which (re) emits the [Input]s
 * it has received from the underlying technology (like Swing or LibGDX).
 */

interface InputEmitter {

    /**
     * Adds an [InputListener] listener to this [InputEmitter]. It will be notified when any
     * kind of [Input] is received by this object.
     */
    fun onInput(listener: InputListener): Subscription

    /**
     * Adds a [KeyStrokeListener] listener to this [InputEmitter]. It will be notified when a
     * [org.hexworks.zircon.api.input.KeyStroke] is received by this object.
     */
    fun onKeyStroke(listener: KeyStrokeListener): Subscription {
        return onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                input.asKeyStroke().map {
                    listener.keyStroked(it)
                }
            }
        })
    }

    /**
     * Adds a [KeyStrokeListener] listener to this [InputEmitter]. It will be notified when a
     * [org.hexworks.zircon.api.input.KeyStroke] is received by this object and matches the
     * given key combination.
     */
    fun onKeyCombination(keyCombination: KeyCombination,
                         listener: Consumer<KeyStroke>): Subscription {
        val (char, inputType, shiftState, ctrlState, altState) = keyCombination
        return onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                input.asKeyStroke().map { ks ->
                    if (shiftState.matches(ks)
                            && ctrlState.matches(ks)
                            && altState.matches(ks)
                            && char == ks.getCharacter()
                            && inputType == ks.inputType()) {
                        listener.accept(ks)
                    }
                }
            }
        })
    }

    fun onKeyPressed(key: Char,
                     listener: Consumer<KeyStroke>): Subscription {
        return onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                input.asKeyStroke().map { ks ->
                    if (ks.getCharacter() == key) {
                        listener.accept(ks)
                    }
                }
            }
        })
    }

    fun onInputOfType(inputType: InputType,
                      listener: Consumer<Input>): Subscription {
        return onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                if (input.inputType() == inputType) {
                    listener.accept(input)
                }
            }
        })
    }

    fun onMouseAction(consumer: Consumer<MouseAction>): Subscription {
        return onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                input.asMouseAction().map {
                    consumer.accept(it)
                }
            }
        })
    }

    /**
     * Adds a [MouseListener] listener to this [InputEmitter]. It will be notified when a
     * [org.hexworks.zircon.api.input.MouseAction] is received by this object.
     */
    fun onMouseAction(listener: MouseListener): Subscription {
        return onInput(object : InputListener {
            override fun inputEmitted(input: Input) {
                input.asMouseAction().map {
                    when (it.actionType) {
                        MOUSE_CLICKED -> listener.mouseClicked(it)
                        MOUSE_PRESSED -> listener.mousePressed(it)
                        MOUSE_RELEASED -> listener.mouseReleased(it)
                        MOUSE_ENTERED -> listener.mouseEntered(it)
                        MOUSE_EXITED -> listener.mouseExited(it)
                        MOUSE_WHEEL_ROTATED_UP -> listener.mouseWheelRotatedUp(it)
                        MOUSE_WHEEL_ROTATED_DOWN -> listener.mouseWheelRotatedDown(it)
                        MOUSE_DRAGGED -> listener.mouseDragged(it)
                        MOUSE_MOVED -> listener.mouseMoved(it)
                    }
                }
            }
        })
    }

    fun onMouseClicked(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseClicked(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMousePressed(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mousePressed(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseReleased(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseReleased(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseEntered(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseEntered(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseExited(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseExited(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseWheelRotatedUp(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseWheelRotatedUp(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseWheelRotatedDown(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseWheelRotatedDown(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseDragged(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseDragged(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }

    fun onMouseMoved(consumer: Consumer<MouseAction>): Subscription {
        return onMouseAction(object : MouseListener {
            override fun mouseMoved(action: MouseAction) {
                consumer.accept(action)
            }
        })
    }
}
