package org.hexworks.zircon.internal.data.tree

import kotlinx.collections.immutable.PersistentList
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch
import kotlin.math.max

class ConcurrentTree<D : Any?>(
        private val treeScope: CoroutineScope = CoroutineScope(Dispatchers.Default + SupervisorJob()),
        private val channel: Channel<Message<D>> = Channel(Channel.BUFFERED)
) : Tree<D> {

    sealed class Message<D : Any?> {

        data class AddChildTo<D : Any?>(
                val parent: TreeNode<D>,
                val child: TreeNode<D>
        ) : Message<D>()

        data class DeleteChild<D : Any?>(
                val child: TreeNode<D>
        ) : Message<D>()
    }

    init {
        treeScope.launch {
            for (msg in channel) {
                when (msg) {
                    is Message.AddChildTo<D> -> {
                        val (parent, child) = msg
                        val lastChildIdx = backend.indexOfLast { it.parent == parent }
                        val parentIdx = backend.indexOf(parent)
                        val idx = max(lastChildIdx, parentIdx) + 1
                        backend = if (idx == backend.lastIndex) {
                            backend.add(child)
                        } else {
                            backend.add(idx, child)
                        }
                    }
                    is Message.DeleteChild<D> -> {
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
    }

    private val root = ConcurrentTreeNode(
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
            channel.send(Message.AddChildTo(parent, child))
        }
    }

    fun remove(node: TreeNode<D>) {
        treeScope.launch {
            channel.send(Message.DeleteChild(node))
        }
    }

    override fun toString(): String {
        return "Tree(descendants=${descendants.joinToString()})"
    }
}
