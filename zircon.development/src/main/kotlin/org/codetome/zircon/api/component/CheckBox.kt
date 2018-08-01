package org.codetome.zircon.api.component

interface CheckBox<T: Any, S: Any> : Component<T, S> {

    fun getText(): String

    fun isChecked(): Boolean
}
