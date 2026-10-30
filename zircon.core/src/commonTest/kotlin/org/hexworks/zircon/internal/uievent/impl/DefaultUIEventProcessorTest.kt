package org.hexworks.zircon.internal.uievent.impl

import io.kotest.matchers.collections.shouldHaveSize
import io.kotest.matchers.shouldBe
import io.kotest.matchers.string.shouldStartWith
import io.kotest.matchers.types.shouldBeInstanceOf
import org.hexworks.zircon.ErrorRecorder
import org.hexworks.zircon.api.uievent.KeyCode
import org.hexworks.zircon.api.uievent.KeyboardEvent
import org.hexworks.zircon.api.uievent.KeyboardEventType
import org.hexworks.zircon.api.uievent.Pass
import org.hexworks.zircon.api.uievent.Processed
import org.hexworks.zircon.api.uievent.UIEventPhase
import kotlin.test.AfterTest
import kotlin.test.Test

class DefaultUIEventProcessorTest {

    private val target = DefaultUIEventProcessor()
    private val errors = ErrorRecorder()
    private val event = KeyboardEvent(KeyboardEventType.KEY_PRESSED, KeyCode.KEY_A, "a")

    @AfterTest
    fun tearDown() {
        errors.uninstall()
    }

    @Test
    fun a_listener_that_throws_is_reported_and_the_others_still_run() {
        var invoked = 0
        target.processKeyboardEvents(KeyboardEventType.KEY_PRESSED) { _, _ -> throw IllegalStateException("boom") }
        target.processKeyboardEvents(KeyboardEventType.KEY_PRESSED) { _, _ -> invoked++ }

        val result = target.process(event, UIEventPhase.TARGET)

        result shouldBe Processed
        invoked shouldBe 1
        errors.errors shouldHaveSize 1
        errors.sources.single() shouldStartWith "UI event listener for KEY_PRESSED"
        errors.errors.single().second.shouldBeInstanceOf<IllegalStateException>()
    }

    @Test
    fun a_listener_that_throws_counts_as_pass() {
        target.handleKeyboardEvents(KeyboardEventType.KEY_PRESSED) { _, _ -> throw IllegalStateException("boom") }

        target.process(event, UIEventPhase.TARGET) shouldBe Pass
    }

    @Test
    fun a_listener_that_threw_stays_subscribed() {
        var invoked = 0
        target.processKeyboardEvents(KeyboardEventType.KEY_PRESSED) { _, _ ->
            invoked++
            throw IllegalStateException("boom")
        }

        target.process(event, UIEventPhase.TARGET)
        target.process(event, UIEventPhase.TARGET)

        invoked shouldBe 2
        errors.errors shouldHaveSize 2
    }
}
