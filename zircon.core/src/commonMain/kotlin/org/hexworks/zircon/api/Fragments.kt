package org.hexworks.zircon.api

import org.hexworks.zircon.api.builder.fragment.MultiSelectBuilder
import org.hexworks.zircon.api.component.Fragment
import kotlin.jvm.JvmStatic

/**
 * Provides builders for [Fragment]s (more complex Components that come with own logic).
 */
object Fragments {

    @JvmStatic
    fun <M : Any> multiSelect(width: Int, values: List<M>) = MultiSelectBuilder.newBuilder(width, values)

}