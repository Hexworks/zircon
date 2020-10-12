package org.hexworks.zircon.internal.data.tree

import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import org.hexworks.cobalt.core.platform.runTest
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertTrue

class ConcurrentTreeTest {

    private lateinit var scope: CoroutineScope
    private lateinit var target: ConcurrentTree<String>
    private lateinit var channel: Channel<ConcurrentTree.Message<String>>

    @BeforeTest
    fun setUp() {
        scope = CoroutineScope(Dispatchers.Unconfined)
        target = ConcurrentTree(scope)
        channel = Channel(Channel.BUFFERED)
    }

    @AfterTest
    fun tearDown() {
        scope.cancel()
    }

    @Test
    fun simpleUsage() = runTest {
        target.createChild("0")
        target.createChild("1")

        while(channel.isEmpty.not()) {
            delay(1)
        }

        assertTrue(target.descendants.map { it.data }.joinToString() == "0, 1")
    }

    @Test
    fun nestedUsage() = runTest {
        val child0 = target.createChild("0")
        child0.createChild("00")
        val child1 = target.createChild("1")
        child1.createChild("10")
        child1.createChild("11")

        while(channel.isEmpty.not()) {
            delay(1)
        }

        assertTrue(target.descendants.map { it.data }.joinToString() == "0, 00, 1, 10, 11")
    }

    @Test
    fun deleteSubtarget() = runTest {
        val child0 = target.createChild("0")
        child0.createChild("00")
        val child1 = target.createChild("1")
        child1.createChild("10")
        child1.createChild("11")

        child1.remove()

        while(channel.isEmpty.not()) {
            delay(1)
        }

        assertTrue(target.descendants.map { it.data }.joinToString() == "0, 00")
    }

    @Test
    fun deleteLeaf() = runTest {
        val child0 = target.createChild("0")
        child0.createChild("00")
        val child1 = target.createChild("1")
        child1.createChild("10")
        val child11 = child1.createChild("11")

        child11.remove()

        while(channel.isEmpty.not()) {
            delay(1)
        }

        assertTrue(target.descendants.map { it.data }.joinToString() == "0, 00, 1, 10")
    }
}
