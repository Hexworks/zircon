package org.hexworks.zircon.internal.component.impl.texteditor

interface Transformation {
    fun apply(editorState: EditorState): EditorState
}