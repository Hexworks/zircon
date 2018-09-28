package org.hexworks.zircon.internal.component.impl.log

abstract class LogElement {
    abstract fun getText(): String
    fun length() = getText().length
}

data class TextElement(val text: String) : LogElement() {
    override fun getText(): String {
        return text
    }
}

data class HyperLinkElement(val linkText: String, val linkId: String) : LogElement() {
    override fun getText(): String {
        return linkText
    }
}



