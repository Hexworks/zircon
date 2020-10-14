package org.hexworks.zircon.internal.data.tree

interface Tree<D : Any?> {

    val descendants: Sequence<TreeNode<D>>
    val children: Sequence<TreeNode<D>>

    fun createChild(data: D? = null): RemovableTreeNode<D>

    companion object {

        fun <D : Any> create(): Tree<D> {
            return ConcurrentTree()
        }
    }
}
