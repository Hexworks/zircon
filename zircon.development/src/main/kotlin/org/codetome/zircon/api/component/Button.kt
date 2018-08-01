package org.codetome.zircon.api.component

interface Button<T: Any, S: Any> : Component<T, S> {

    fun getText(): String
}
