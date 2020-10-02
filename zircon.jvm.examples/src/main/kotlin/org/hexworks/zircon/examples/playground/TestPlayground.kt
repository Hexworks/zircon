@file:Suppress("UNUSED_VARIABLE", "EXPERIMENTAL_API_USAGE")

package org.hexworks.zircon.examples.playground

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.actor
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import org.hexworks.zircon.examples.playground.TestPlayground.ConcurrentTree.Message.AddChildTo
import kotlin.math.max

object TestPlayground {

    interface Tree<D : Any> {

        val descendants: Sequence<TreeNode<D>>
        val children: Sequence<TreeNode<D>>

        fun createChild(data: D? = null): TreeNode<D>

        companion object {

            fun <D : Any> create(): Tree<D> {
                return ConcurrentTree()
            }
        }
    }

    interface TreeNode<D : Any> {
        val data: D?
        val parent: TreeNode<D>?
        val hasParent: Boolean
            get() = parent != null
        val children: Sequence<TreeNode<D>>

        fun createChild(data: D? = null): TreeNode<D>

        fun remove()
    }

    @Suppress("UNCHECKED_CAST")
    class ConcurrentTree<D : Any> : Tree<D> {

        sealed class Message {

            data class AddChildTo<D : Any>(
                    val parent: TreeNode<D>,
                    val child: TreeNode<D>
            ) : Message()

            data class DeleteChild<D : Any>(
                    val child: TreeNode<D>
            ) : Message()
        }

        private val treeScope = CoroutineScope(Dispatchers.Default + SupervisorJob())

        private val actor = treeScope.actor<Message> {
            for (msg in channel) { // iterate over incoming messages
                when (msg) {
                    is AddChildTo<*> -> {
                        val (parent, child) = msg
                        val lastChildIdx = backend.indexOfLast { it.parent == parent }
                        val parentIdx = backend.indexOf(parent)
                        val idx = max(lastChildIdx, parentIdx) + 1
                        backend = if (idx == backend.lastIndex) {
                            backend.add(child as TreeNode<D>)
                        } else {
                            backend.add(idx, child as TreeNode<D>)
                        }
                    }
                    is Message.DeleteChild<*> -> {
                        val parent = msg.child.parent
                        val child = msg.child
                        val toDelete = backend
                                .dropWhile { it !== child }
                                .takeWhile { it === child || it.parent !== parent }
                        backend = backend.removeAll(toDelete)
                    }
                }
            }
        }

        private val root = RootNode(
                parent = null,
                data = null,
                tree = this
        )
        private var backend: PersistentList<TreeNode<D>> = persistentListOf(root)

        override val descendants: Sequence<TreeNode<D>>
            get() = backend.asSequence().drop(1)

        override val children: Sequence<TreeNode<D>>
            get() = root.children

        override fun createChild(data: D?) = root.createChild(data)

        fun addChildTo(parent: TreeNode<D>, child: TreeNode<D>) {
            treeScope.launch {
                actor.send(AddChildTo(parent, child))
            }
        }

        fun remove(node: TreeNode<D>) {
            treeScope.launch {
                actor.send(Message.DeleteChild(node))
            }
        }

        override fun toString(): String {
            return "Tree(descendants=${descendants.joinToString()})"
        }
    }

    open class ConcurrentTreeNode<D : Any>(
            override val parent: ConcurrentTreeNode<D>? = null,
            override val data: D? = null,
            private val tree: ConcurrentTree<D>
    ) : TreeNode<D> {

        override val children: Sequence<TreeNode<D>>
            get() = tree.children.filter { it.parent == this }

        override fun createChild(data: D?): ConcurrentTreeNode<D> {
            val parent = this
            return ConcurrentTreeNode(
                    parent = this,
                    data = data,
                    tree = tree
            ).apply {
                tree.addChildTo(parent, this)
            }
        }

        override fun remove() {
            tree.remove(this)
        }

        override fun toString(): String {
            return "TreeNode(data=$data)"
        }
    }

    private class RootNode<D : Any>(
            parent: ConcurrentTreeNode<D>? = null,
            data: D? = null,
            tree: ConcurrentTree<D>
    ) : ConcurrentTreeNode<D>(
            parent = parent,
            data = data,
            tree = tree
    ) {
        override fun remove() {
            throw RuntimeException("A root node can't be removed.")
        }
    }

    @JvmStatic
    fun main(args: Array<String>) = runBlocking {

        simpleUsage()
        nestedUsage()
        deleteSubtree()
        deleteLeaf()

    }

    private fun simpleUsage() {
        val tree = ConcurrentTree<String>()

        val child0 = tree.createChild("0")
        val child1 = tree.createChild("1")

        Thread.sleep(500)

        println(tree.descendants.map { it.data }.joinToString())
    }

    private fun nestedUsage() {
        val tree = ConcurrentTree<String>()

        val child0 = tree.createChild("0")
        val child00 = child0.createChild("00")
        val child1 = tree.createChild("1")
        val child10 = child1.createChild("10")
        val child11 = child1.createChild("11")

        Thread.sleep(500)

        println(tree.descendants.map { it.data }.joinToString())
    }

    private fun deleteSubtree() {
        val tree = ConcurrentTree<String>()

        val child0 = tree.createChild("0")
        val child00 = child0.createChild("00")
        val child1 = tree.createChild("1")
        val child10 = child1.createChild("10")
        val child11 = child1.createChild("11")

        child1.remove()

        Thread.sleep(500)

        println(tree.descendants.map { it.data }.joinToString())
    }

    private fun deleteLeaf() {
        val tree = ConcurrentTree<String>()

        val child0 = tree.createChild("0")
        val child00 = child0.createChild("00")
        val child1 = tree.createChild("1")
        val child10 = child1.createChild("10")
        val child11 = child1.createChild("11")

        child11.remove()

        Thread.sleep(500)

        println(tree.descendants.map { it.data }.joinToString())
    }

}