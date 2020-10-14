package org.hexworks.zircon.internal.data.tree

interface TreeNode<D : Any?> {

    val data: D?
    val parent: TreeNode<D>?
    val hasParent: Boolean
        get() = parent != null
    val children: Sequence<TreeNode<D>>

    /**
     * Creates a child for this [TreeNode].
     */
    fun createChild(data: D? = null): RemovableTreeNode<D>

}
