package org.hexworks.zircon.api.builder.fragment

import org.hexworks.zircon.api.component.Component
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size
import org.hexworks.zircon.api.data.extensions.isSizeNotUnknown
import org.hexworks.zircon.api.dsl.AnyContainerBuilder
import org.hexworks.zircon.api.dsl.ZirconDsl
import org.hexworks.zircon.api.dsl.buildFragmentFor
import org.hexworks.zircon.api.fragment.MatrixTabBar
import org.hexworks.zircon.api.fragment.MatrixTabBar.Orientation
import org.hexworks.zircon.api.fragment.Tab
import org.hexworks.zircon.api.fragment.builder.FragmentBuilder
import org.hexworks.zircon.internal.fragment.impl.DefaultMatrixTabBar
import org.hexworks.zircon.internal.fragment.impl.DefaultTab

/** A tab of one axis of a [MatrixTabBar]: a key and a label, no content of its own. */
@ZirconDsl
public class MatrixTabBuilder {
    public var key: String? = null
    public var label: String? = null

    internal fun build(width: Int?): Tab {
        val key = key
        val label = label
        require(key != null && key.isNotBlank()) { "A tab must have a non-blank key." }
        require(label != null) { "A tab must have a label" }
        return DefaultTab(key = key, initialText = label, width = width ?: (label.length + 2))
    }
}

/** The content shown at one (horizontal, vertical) pair of tabs of a [MatrixTabBar]. */
@ZirconDsl
public class MatrixViewBuilder {
    public var horizontal: String? = null
    public var vertical: String? = null
    public var content: Component? = null
}

/**
 * Builds a [MatrixTabBar]. Tabs are shown in the order they are added on both axes.
 * [tabWidth] is the width of the vertical strip; the horizontal strip is 3 high.
 */
@ZirconDsl
public class MatrixTabBarBuilder : FragmentBuilder<MatrixTabBar> {

    override var position: Position = Position.ZERO
    public var size: Size = Size.UNKNOWN
    public var tabWidth: Int = -1

    /** The strip that shows all of its tabs and filters the other one. */
    public var primary: Orientation = Orientation.HORIZONTAL

    /** The horizontal tab selected at first; the first one when `null`. */
    public var defaultHorizontal: String? = null

    /** The vertical tab selected at first; the first one with a view at [defaultHorizontal] when `null`. */
    public var defaultVertical: String? = null

    private val horizontalTabs = ArrayList<MatrixTabBuilder>()
    private val verticalTabs = ArrayList<MatrixTabBuilder>()
    private val views = ArrayList<MatrixViewBuilder>()

    /** The size of the content panel: the bar minus the two strips. */
    public val contentSize: Size
        get() {
            require(tabWidth >= 3) { "tabWidth must be set before using contentSize" }
            return Size(size.width - tabWidth, size.height - 3)
        }

    public fun horizontalTab(init: MatrixTabBuilder.() -> Unit) {
        horizontalTabs += MatrixTabBuilder().apply(init)
    }

    public fun verticalTab(init: MatrixTabBuilder.() -> Unit) {
        verticalTabs += MatrixTabBuilder().apply(init)
    }

    public fun view(init: MatrixViewBuilder.() -> Unit) {
        views += MatrixViewBuilder().apply(init)
    }

    override fun build(): MatrixTabBar {
        require(size.isSizeNotUnknown) { "A matrix tab bar must have a size" }
        require(tabWidth >= 3) { "tabWidth must be at least 3" }
        require(size.width > tabWidth + 3 && size.height > 6) {
            "A matrix tab bar of size $size has no room for content beside a $tabWidth wide strip"
        }
        require(horizontalTabs.isNotEmpty()) { "A matrix tab bar must have at least 1 horizontal tab" }
        require(verticalTabs.isNotEmpty()) { "A matrix tab bar must have at least 1 vertical tab" }
        require(views.isNotEmpty()) { "A matrix tab bar must have at least 1 view" }

        val hTabs = horizontalTabs.map { it.build(width = null) }
        val vTabs = verticalTabs.map { it.build(width = tabWidth) }
        val hKeys = hTabs.map { it.key }
        val vKeys = vTabs.map { it.key }
        require(hKeys.toSet().size == hKeys.size) { "A matrix tab bar can't have duplicate horizontal keys." }
        require(vKeys.toSet().size == vKeys.size) { "A matrix tab bar can't have duplicate vertical keys." }

        val cells = LinkedHashMap<Pair<String, String>, Component>()
        views.forEach { view ->
            val h = view.horizontal
            val v = view.vertical
            val content = view.content
            require(h != null && h in hKeys) { "There is no horizontal tab with the key $h" }
            require(v != null && v in vKeys) { "There is no vertical tab with the key $v" }
            require(content != null) { "The view at ($h, $v) must have content" }
            require(cells.put(h to v, content) == null) { "There is already a view at ($h, $v)" }
        }
        hKeys.forEach { h -> require(cells.keys.any { it.first == h }) { "The horizontal tab $h has no view" } }
        vKeys.forEach { v -> require(cells.keys.any { it.second == v }) { "The vertical tab $v has no view" } }

        defaultHorizontal?.let { require(it in hKeys) { "There is no horizontal tab with the key $it" } }
        defaultVertical?.let { require(it in vKeys) { "There is no vertical tab with the key $it" } }
        val h: String
        val v: String
        if (primary == Orientation.HORIZONTAL) {
            h = defaultHorizontal ?: hKeys.first()
            v = defaultVertical ?: vKeys.first { cells.containsKey(h to it) }
        } else {
            v = defaultVertical ?: vKeys.first()
            h = defaultHorizontal ?: hKeys.first { cells.containsKey(it to v) }
        }
        require(cells.containsKey(h to v)) { "There is no view at the default selection ($h, $v)" }

        return DefaultMatrixTabBar(
            size = size,
            tabWidth = tabWidth,
            primary = primary,
            horizontalTabs = hTabs,
            verticalTabs = vTabs,
            views = cells,
            defaultHorizontal = h,
            defaultVertical = v,
        ).also { it.root.moveTo(position) }
    }
}

/**
 * Creates a new [MatrixTabBar] using the fragment builder DSL and returns it.
 */
public fun matrixTabBar(
    init: MatrixTabBarBuilder.() -> Unit
): MatrixTabBar = MatrixTabBarBuilder().apply(init).build()

/**
 * Creates a new [MatrixTabBar] using the fragment builder DSL, adds it to the
 * receiver [AnyContainerBuilder] and returns the [MatrixTabBar].
 */
public fun AnyContainerBuilder.matrixTabBar(
    init: MatrixTabBarBuilder.() -> Unit
): MatrixTabBar = buildFragmentFor(this, MatrixTabBarBuilder(), init)
