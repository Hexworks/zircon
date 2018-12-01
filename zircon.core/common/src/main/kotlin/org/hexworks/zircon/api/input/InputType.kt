package org.hexworks.zircon.api.input

/**
 * This enum is a categorization of the various keys available on a normal computer keyboard that are usable
 * (detectable) by a grid environment and mouse events (drag, click, etc).
 * For ordinary numbers, letters and symbols, the enum value is *Character*
 * but please keep in mind that newline and tab, usually represented by \n and \t, are considered their own separate
 * values by this enum (*Enter* and *Tab*).
 */
enum class InputType {
    /**
     * This value corresponds to a regular character 'typed', usually alphanumeric or a symbol.
     * The one special case here is the enter key which could be expected to be returned as a '\n'
     * character but is actually returned as a separate `InputType` (see below).
     * Tab, backspace and some others works this way too.
     */
    Character,
    Pause,
    CapsLock,
    Space,
    Escape,
    Backspace,
    ArrowLeft,
    ArrowRight,
    ArrowUp,
    ArrowDown,
    Insert,
    Delete,
    Home,
    End,
    PageUp,
    PageDown,
    Tab,
    ReverseTab,
    Enter,
    F1,
    F2,
    F3,
    F4,
    F5,
    F6,
    F7,
    F8,
    F9,
    F10,
    F11,
    F12,
    F13,
    F14,
    F15,
    F16,
    F17,
    F18,
    F19,
    Numpad0,
    Numpad1,
    Numpad2,
    Numpad3,
    Numpad4,
    Numpad5,
    Numpad6,
    Numpad7,
    Numpad8,
    Numpad9,
    NumpadAdd,
    NumpadSubtract,
    NumpadMultiply,
    NumpadDivide,
    Unknown,
    /**
     * This value is only internally to understand where the cursor currently is, it's not expected to
     * be returned by the API to an input read call.
     */
    CursorLocation,
    /**
     * This type is for mouse events (click, move, etc).
     */
    MouseEvent,
    /**
     * This value is returned when you try to read input and the input stream has been closed.
     */
    EOF
}

