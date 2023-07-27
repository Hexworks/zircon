package org.hexworks.zircon.api.component

/**
 * A [Fragment] is a reusable container for a [Component]. This [Component]
 * can be accessed by [root].
 *
 * A [Fragment] usually contains view logic that works with the controls that are
 * present in [root].
 */
interface Fragment {

    /**
     * The [Component] this [Fragment] contains.
     */
    val root: Component
}
