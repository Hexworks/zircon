package org.codetome.zircon.api.component

interface Header<T: Any, S: Any> : Component<T, S> {

    fun getText(): String
}
