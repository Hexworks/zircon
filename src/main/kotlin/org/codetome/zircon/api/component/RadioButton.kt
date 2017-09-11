package org.codetome.zircon.api.component

interface RadioButton : Component {

    fun getText(): String

    fun isSelected(): Boolean

    fun select()

    fun removeSelection()

}