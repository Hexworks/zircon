package org.codetome.zircon.api.component

interface Label<T: Any, S: Any> : Component<T, S> {

    fun getText(): String
}
