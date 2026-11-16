package org.hexworks.zircon.api.component

import org.hexworks.zircon.api.behavior.TextOverride

/**
 * A single-line text field. The text scrolls horizontally when it is longer than the field;
 * `Enter` is not consumed, so it can activate something else on the screen.
 */
public interface Input : Component, TextOverride {

    /**
     * The index in [text] the cursor is at: `0` is before the first character,
     * `text.length` after the last one.
     */
    public val cursorIndex: Int

    /**
     * The index of the first visible character, when the text is longer than the field.
     */
    public val scrollOffset: Int

    /**
     * The most characters the field accepts, or `null` for no limit.
     */
    public val maxLength: Int?
}
