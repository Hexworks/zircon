package org.hexworks.zircon.internal.component.impl.textedit

/**
 * A [TextBufferTransformation] is a Command pattern for modifying
 * [EditableTextBuffer]s.
 */
interface TextBufferTransformation {

    /**
     * Applies this transformation to the given text buffer.
     */
    fun applyTo(buffer: EditableTextBuffer)
}
