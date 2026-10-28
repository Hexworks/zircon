package org.hexworks.zircon.internal.component.impl.texteditor

public interface Transformation {
    public fun apply(editorState: EditorState): EditorState
}