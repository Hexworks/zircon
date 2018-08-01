package org.codetome.zircon.api.component

interface Panel<T: Any, S: Any> : Container<T, S> {

    fun getTitle(): String
}
