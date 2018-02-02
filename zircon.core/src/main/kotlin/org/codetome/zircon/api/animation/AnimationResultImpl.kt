package org.codetome.zircon.api.animation

import java.util.concurrent.TimeUnit
import java.util.concurrent.locks.ReentrantLock

class AnimationResultImpl(private var state: AnimationState): AnimationResult {

    private val lock = ReentrantLock()
    private val finishCond = lock.newCondition()

    override fun isFinished() = state == AnimationState.FINISHED

    override fun isRunning() = state == AnimationState.IN_PROGRESS

    override fun isInfinite() = state == AnimationState.INFINITE

    override fun waitUntilFinish(timeout: Long, timeUnit: TimeUnit): AnimationResultImpl {
        require(state != AnimationState.INFINITE) {
            "Can't wait for an infinite Animation to finish."
        }
        lock.lock()
        finishCond.await(timeout, timeUnit)
        return this
    }

    fun setState(state: AnimationState) {
        this.state = state
        if(state == AnimationState.FINISHED) {
            lock.lock()
            finishCond.signalAll()
            lock.unlock()
        }
    }

}

