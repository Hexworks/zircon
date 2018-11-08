package org.hexworks.zircon.internal.component.impl.textedit

import org.hexworks.zircon.internal.component.impl.textedit.cursor.Cursor
import org.hexworks.zircon.platform.util.SystemUtils

class DefaultEditableTextBuffer(source: String,
                                override var cursor: Cursor) : EditableTextBuffer {

    override val textBuffer: MutableList<MutableList<Char>> = source
            .split(SystemUtils.getLineSeparator())
            .asSequence()
            .map { it.toMutableList() }
            .toMutableList()

    override fun applyTransformation(transformation: TextBufferTransformation) = also {
        transformation.applyTo(this)
    }

}
