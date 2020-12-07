package org.hexworks.zircon.internal.data.tree

open class ConcurrentTreeNode<D : Any?>(
    override val parent: ConcurrentTreeNode<D>? = null,
    override val data: D? = null,
    private val tree: ConcurrentTree<D>
) : RemovableTreeNode<D> {

    override val children: Sequence<TreeNode<D>>
        get() = tree.children.filter { it.parent == this }

    override fun createChild(data: D?): RemovableTreeNode<D> {
        val parent = this
        return ConcurrentTreeNode(
            parent = this,
            data = data,
            tree = tree
        ).also { child ->
            tree.addChildTo(parent, child)
        }
    }

    override fun remove() {
        tree.remove(this)
    }

    override fun toString(): String {
        return "ConcurrentTreeNode(data=$data)"
    }
}
