package org.hexworks.zircon.internal.component.impl.textedit.transformation

import org.assertj.core.api.Assertions.assertThat
import org.hexworks.zircon.internal.component.impl.textedit.DefaultEditableTextBuffer
import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.junit.Test

class InsertCharacterTest {

    @Test
    fun shouldProperlyInsertCharacterAndMoveCursorsWhenInserting() {
        val cursor = Cursor(0, 1)

        val buffer = generateBuffer(cursor)

        val target = InsertCharacter(CHAR_TO_INSERT)

        target.applyTo(buffer)

        assertThat(buffer.cursor).isEqualTo(
            cursor.withRelativeColumn(1)
        )
    }

    companion object {
        private const val CHAR_TO_INSERT = 'x'
        private const val OTHER_CHAR = 'a'

        fun generateBuffer(cursor: Cursor) = DefaultEditableTextBuffer(
            source = "$OTHER_CHAR$OTHER_CHAR$OTHER_CHAR$OTHER_CHAR",
            cursor = cursor
        )
    }
}
