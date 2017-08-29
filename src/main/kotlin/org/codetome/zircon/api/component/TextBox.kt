package org.codetome.zircon.api.component

import org.codetome.zircon.api.behavior.Scrollable

interface TextBox : Component, Scrollable {

    fun getText(): String

    fun setText(text: String)

    /**
     * Enables this [Component] which means that its functionality will
     * work from now on (has no effect if the component was already enabled.
     */
    fun enable(): Unit {

    }

    /**
     * Disables this [Component] and its functionality will no longer work.
     * has no effect if this component was already disabled.
     */
    fun disable(): Unit {

    }

}