package org.hexworks.zircon.internal.fragment.impl

import org.hexworks.zircon.api.builder.fragment.TabBuilder
import org.hexworks.zircon.api.data.Position
import org.hexworks.zircon.api.data.Size

abstract class TabBarBuilder(
    var size: Size,
    var defaultSelected: String?,
    var position: Position,
    internal var tabs: List<TabBuilder>
) {

    protected fun checkCommonProperties() {
        require(size.isNotUnknown) {
            "A tab bar must has a size"
        }
        require(size > Size.create(3, 3)) {
            "Can't create a tab bar that's smaller than 3x3"
        }
        require(tabs.isNotEmpty()) {
            "A tab bar must have at least 1 tab"
        }
        val tabKeys = tabs.map { it.key }.toSet()
        defaultSelected?.let {
            require(tabKeys.contains(defaultSelected)) {
                "There is no tab with the key $defaultSelected"
            }
        }
        require(tabKeys.size == tabs.size) {
            "A tab bar can't have duplicate keys."
        }
    }
}
