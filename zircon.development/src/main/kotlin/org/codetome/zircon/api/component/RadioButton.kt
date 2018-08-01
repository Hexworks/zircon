package org.codetome.zircon.api.component

interface RadioButton<T: Any, S: Any> : Component<T, S> {

    fun getText(): String

    fun isSelected(): Boolean

}
