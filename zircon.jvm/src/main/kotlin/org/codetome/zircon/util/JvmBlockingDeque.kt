package org.codetome.zircon.util

import java.util.concurrent.BlockingDeque
import java.util.concurrent.LinkedBlockingDeque

class JvmBlockingDeque<E>(private val backend: BlockingDeque<E> = LinkedBlockingDeque())
    : org.codetome.zircon.util.BlockingDeque<E>, MutableCollection<E> by backend {

    override fun offer(e: E): Boolean = backend.offer(e)

    override fun drainTo(c: MutableCollection<E>): Int = backend.drainTo(c)

    override fun remove(element: E): Boolean = backend.remove(element)

    override fun pollLast(): Maybe<E> = Maybe.ofNullable(backend.pollLast())
}
