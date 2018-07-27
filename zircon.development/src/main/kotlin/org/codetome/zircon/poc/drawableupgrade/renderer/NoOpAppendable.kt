package org.codetome.zircon.poc.drawableupgrade.renderer

object NoOpAppendable : Appendable {
    override fun append(csq: CharSequence?): java.lang.Appendable {
        return this
    }

    override fun append(csq: CharSequence?, start: Int, end: Int): java.lang.Appendable {
        return this
    }

    override fun append(c: Char): java.lang.Appendable {
        return this
    }
}
