package org.hexworks.zircon.internal.data.tree

interface RemovableTreeNode<D: Any?> : TreeNode<D> {

    /**
     * Removes this [TreeNode] from its parent.
     */
    fun remove()

}
