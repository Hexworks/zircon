package org.codetome.zircon.screen

/**
 * What to do about the tab character when putting on a [Screen]. Since tabs are a bit special, their meaning
 * depends on which column the cursor is in when it's printed, we'll need to have some way to tell
 * the [Screen] what to do when encountering a tab character.
 */
enum class TabBehavior(private val replaceFactor: Int, private val alignFactor: Int) {

    /**
     * Tab characters are replaced with a single blank space, no matter where the tab was placed.
     */
    CONVERT_TO_ONE_SPACE(1, Int.MIN_VALUE),
    /**
     * Tab characters are replaced with two blank spaces, no matter where the tab was placed.
     */
    CONVERT_TO_TWO_SPACES(2, Int.MIN_VALUE),
    /**
     * Tab characters are replaced with three blank spaces, no matter where the tab was placed.
     */
    CONVERT_TO_THREE_SPACES(3, Int.MIN_VALUE),
    /**
     * Tab characters are replaced with four blank spaces, no matter where the tab was placed.
     */
    CONVERT_TO_FOUR_SPACES(4, Int.MIN_VALUE),
    /**
     * Tab characters are replaced with eight blank spaces, no matter where the tab was placed.
     */
    CONVERT_TO_EIGHT_SPACES(8, Int.MIN_VALUE),
    /**
     * Tab characters are replaced with enough space characters to reach the next column index that is evenly divisible
     * by 4, simulating a normal tab character when placed inside a text document.
     */
    ALIGN_TO_COLUMN_4(Int.MIN_VALUE, 4),
    /**
     * Tab characters are replaced with enough space characters to reach the next column index that is evenly divisible
     * by 8, simulating a normal tab character when placed inside a text document.
     */
    ALIGN_TO_COLUMN_8(Int.MIN_VALUE, 8);

    /**
     * Given a string, being placed on the screen at column X, returns the same string with all tab characters (\t)
     * replaced according to this TabBehaviour.
     */
    fun replaceTabs(string: String, columnIndex: Int): String {
        var result = string
        var tabPosition = result.indexOf('\t')
        while (tabPosition != -1) {
            val tabReplacementHere = getTabReplacement(columnIndex + tabPosition)
            result = result.substring(0, tabPosition) + tabReplacementHere + result.substring(tabPosition + 1)
            tabPosition += tabReplacementHere.length
            tabPosition = result.indexOf('\t', tabPosition)
        }
        return result
    }

    /**
     * Returns the String that can replace a tab at the specified position, according to this TabBehaviour.
     */
    fun getTabReplacement(columnIndex: Int): String {
        val replaceCount: Int
        val replace = StringBuilder()
        if (replaceFactor != UNKNOWN_FACTOR) {
            replaceCount = replaceFactor
        } else if (alignFactor != UNKNOWN_FACTOR) {
            replaceCount = alignFactor - columnIndex % alignFactor
        } else {
            return "\t"
        }
        (0..replaceCount - 1).forEach { replace.append(" ") }
        return replace.toString()
    }

    companion object {
        val DEFAULT_TAB_BEHAVIOR = ALIGN_TO_COLUMN_4
        val UNKNOWN_FACTOR = Int.MIN_VALUE
    }
}
